# MySQL 8.0をDebianベースのイメージとして使用
FROM mysql:8.0-debian

# APTキャッシュをクリア
RUN apt-get clean

# 新しいMySQL GPGキーを追加（修正されたコマンド）
RUN wget https://repo.mysql.com/RPM-GPG-KEY-mysql-2022 -O /etc/apt/trusted.gpg.d/mysql.gpg

# Debianのパッケージリストを更新
RUN apt-get update

# ロケール（地域と言語の設定）のパッケージをインストール
RUN apt-get -y install locales-all

# 環境変数として日本語のロケールを設定
ENV LANG ja_JP.UTF-8
ENV LANGUAGE ja_JP:ja
ENV LC_ALL ja_JP.UTF-8

# ホストの./conf/mysql/my.cnfファイルをコンテナの/etc/my.cnfにコピー
COPY ./conf/mysql/my.cnf /etc/my.cnf
