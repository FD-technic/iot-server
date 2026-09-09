[Console]::OutputEncoding = [System.Text.Encoding]::UTF8

$RemoteUser = "petr"
$RemoteHost = "10.0.1.21"
$RemoteDir = "/var/backups/postgres/iot"
$LocalDir = "$env:USERPROFILE\Documents\github\Apps\FD-IoT\IoT-server\iot-backups"

Write-Host "========================================"
Write-Host "   Stahování PostgreSQL záloh - iot"
Write-Host "========================================"
Write-Host ""

New-Item -ItemType Directory -Force -Path $LocalDir | Out-Null

Write-Host "Hledám posledních 5 záloh na Orange Pi..."
Write-Host ""

$files = ssh "$RemoteUser@$RemoteHost" "ls -1t $RemoteDir/iot_*.dump | head -5"

if (-not $files) {
    Write-Host "Nenalezeny žádné zálohy."
    Read-Host "Stiskni Enter pro ukončení"
    exit 1
}

foreach ($file in $files) {
    $filename = Split-Path $file -Leaf
    $localFile = Join-Path $LocalDir $filename

    if (Test-Path $localFile) {
        Write-Host "[SKIP] $filename - už existuje"
    }
    else {
        Write-Host "[DOWNLOAD] $filename"
        scp "$RemoteUser@$RemoteHost`:$file" "$LocalDir\"
    }
}

Write-Host ""
Write-Host "========================================"
Write-Host "Hotovo."
Write-Host "Zálohy: $LocalDir"
Write-Host "========================================"
Write-Host ""

Read-Host "Stiskni Enter pro ukončení"