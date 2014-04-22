# Logcat Store

Logcat のデータをファイルに保存する。
エラーで端末が落ちてしまい、通常の方法では Logcat のデータを取得できない場合に利用する。
起動するとローカルファイルに Logcat の内容を書き込み続ける。

## 利用方法

### ログ保存の開始
ランチャーもしくは adb を使い、com.lisb.logcatstore.MainActivity を起動する。
```sh:adbでの起動例
adb shell am start -a android.intent.action.MAIN -n com.lisb.logcatstore/.MainActivity
``` 

### ログの取得
MainActivity にファイルの保存パスが表示されているので、そのパスから adb などを使いファイルを取得する。
```sh:adbでの取得例
db pull /data/data/com.lisb.logcatstore/files/logcat.log ~/
```

## 注意点

Jelly Bean 以降は su なしだと当アプリのログしか取得できないため、このアプリの実用価値はない。
