$tcpListener = [System.Net.Sockets.TcpListener]::Create(18080)
$tcpListener.Start()
Write-Host 'Mock sync server listening on http://10.0.2.2:18080/sync/'

try {
    while ($true) {
        $client = $tcpListener.AcceptTcpClient()
        try {
            $stream = $client.GetStream()
            $reader = New-Object System.IO.StreamReader($stream, [Text.Encoding]::ASCII)
            $writer = New-Object System.IO.StreamWriter($stream, [Text.Encoding]::ASCII)
            $writer.NewLine = "`r`n"
            $writer.AutoFlush = $true

            $requestLine = $reader.ReadLine()
            if ([string]::IsNullOrWhiteSpace($requestLine)) {
                continue
            }

            $parts = $requestLine.Split(' ')
            if ($parts.Length -lt 2) {
                continue
            }

            $method = $parts[0]
            $path = $parts[1]

            $contentLength = 0
            while ($true) {
                $line = $reader.ReadLine()
                if ($line -eq $null -or $line.Length -eq 0) { break }
                if ($line -match '^Content-Length:\s*(\d+)$') {
                    $contentLength = [int]$Matches[1]
                }
            }

            $body = ''
            if ($contentLength -gt 0) {
                $buffer = New-Object char[] $contentLength
                $read = 0
                while ($read -lt $contentLength) {
                    $count = $reader.Read($buffer, $read, $contentLength - $read)
                    if ($count -le 0) { break }
                    $read += $count
                }
                $body = -join $buffer
            }

            if ($method -ne 'POST' -or $path -ne '/sync/') {
                $responseText = '{"ok":false,"error":"not found"}'
                $responseBytes = [Text.Encoding]::ASCII.GetBytes($responseText)
                $header = "HTTP/1.1 404 Not Found`r`nContent-Type: application/json`r`nContent-Length: $($responseBytes.Length)`r`nConnection: close`r`n`r`n"
                $writer.Write($header)
                $stream.Write($responseBytes, 0, $responseBytes.Length)
                continue
            }

            Write-Host '--- received sync payload ---'
            Write-Host $body
            Write-Host '-----------------------------'

            $responseText = '{"ok":true}'
            $responseBytes = [Text.Encoding]::ASCII.GetBytes($responseText)
            $header = "HTTP/1.1 200 OK`r`nContent-Type: application/json`r`nContent-Length: $($responseBytes.Length)`r`nConnection: close`r`n`r`n"
            $writer.Write($header)
            $stream.Write($responseBytes, 0, $responseBytes.Length)
        }
        finally {
            if ($client) { $client.Close() }
        }
    }
}
finally {
    $tcpListener.Stop()
}



