# 露科亚启动器 GitHub 发版

## 先做一次登录

```powershell
& "C:\Program Files\GitHub CLI\gh.exe" auth login
```

建议选择：

- `GitHub.com`
- `HTTPS`
- `Login with a web browser`

登录完检查：

```powershell
& "C:\Program Files\GitHub CLI\gh.exe" auth status
```

## 一键发版

在项目目录运行：

```powershell
powershell -ExecutionPolicy Bypass -File .\publish-github-release.ps1 -VersionName 0.8.20 -VersionCode 150
```

脚本会自动做这些事：

1. 更新 `app/build.gradle.kts` 里的版本号
2. 构建 APK
3. 复制一个带版本号的 APK 到 `outputs`
4. 提交当前项目改动
5. 打 tag
6. 推送分支和 tag
7. 创建 GitHub Release 并上传 APK

脚本上传到 GitHub Release 的文件名会固定使用英文：

```text
lukoa-launcher-v版本号.apk
```

这样可以避开 Windows 和 GitHub CLI 在中文文件名下偶发的资产名异常。

## 先做一次自检

如果你只想先确认环境没问题，不想真的提交和发版，可以先跑：

```powershell
powershell -ExecutionPolicy Bypass -File .\publish-github-release.ps1 -VersionName 0.8.20 -VersionCode 150 -ValidateOnly
```

这个模式只检查：

1. `gh` 是否已安装
2. GitHub 是否已登录
3. 当前目录是不是项目仓库根目录
4. 当前分支是否有效

## 可选参数

自定义标题：

```powershell
powershell -ExecutionPolicy Bypass -File .\publish-github-release.ps1 -VersionName 0.8.20 -VersionCode 150 -ReleaseTitle "露科亚启动器 v0.8.20"
```

直接写更新说明：

```powershell
powershell -ExecutionPolicy Bypass -File .\publish-github-release.ps1 -VersionName 0.8.20 -VersionCode 150 -ReleaseNotes "修复启动与多目录选择，优化 GitHub 发版链路。"
```

或者用文件：

```powershell
powershell -ExecutionPolicy Bypass -File .\publish-github-release.ps1 -VersionName 0.8.20 -VersionCode 150 -ReleaseNotesFile .\release-notes.md
```

自动从 `制作进度.md` 生成一份长公告：

```powershell
powershell -ExecutionPolicy Bypass -File .\publish-github-release.ps1 -VersionName 0.8.20 -VersionCode 150 -AutoNotes
```

如果你想明确指定“从哪个旧版本开始写到现在”，可以加：

```powershell
powershell -ExecutionPolicy Bypass -File .\publish-github-release.ps1 -VersionName 0.8.20 -VersionCode 150 -AutoNotes -AutoNotesFrom 0.5.5
```

如果当前版本还有一些没写进 `制作进度.md` 的收尾内容，可以额外补几条：

```powershell
powershell -ExecutionPolicy Bypass -File .\publish-github-release.ps1 -VersionName 0.8.20 -VersionCode 150 -AutoNotes -CurrentHighlights "补齐多酒馆目录选择" -CurrentHighlights "安装前预检接入镜像源检测"
```

只改版本号和发版文案、跳过重新构建：

```powershell
powershell -ExecutionPolicy Bypass -File .\publish-github-release.ps1 -VersionName 0.8.20 -VersionCode 150 -SkipBuild
```
