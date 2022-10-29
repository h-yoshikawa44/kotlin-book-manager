# Book Manager

個人勉強用リポジトリ

教材出典：[Kotlin サーバーサイドプログラミング実践開発](https://gihyo.jp/book/2021/978-4-297-11859-4) ch6～8

## 環境立ち上げ
MySQL コンテナ立ち上げ
```bash
docker-compose up -d
```

フロントエンド（front 直下）
```bash
npm run dev
```

バックエンド  
BookManagerAppliation.kt を実行

## データベース
コンテナに入る
```bash
docker-compose exec db bash
```

MySQL へログイン
```bash
mysql -h 127.0.0.1 --port 3306 -uroot -pmysql
```

### 初回セットアップ
データベース作成
```sql
create database book_manager;
```

使用データベース選択
```sql
use book_manager;
```

テーブル作成
```sql
CREATE TABLE user (
  id bigint NOT NULL,
  email varchar(256) UNIQUE NOT NULL,
  password varchar(128) NOT NULL,
  name varchar(32) NOT NULL,
  role_type enum('ADMIN', 'USER'),
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE book (
  id bigint NOT NULL,
  title varchar(128) NOT NULL,
  author varchar(32) NOT NULL,
  release_date date NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE rental (
  book_id bigint NOT NULL,
  user_id bigint NOT NULL,
  rental_datetime datetime NOT NULL,
  return_deadline datetime NOT NULL,
  PRIMARY KEY (book_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

テストデータの追加
```sql
insert into book values
  (100, 'Kotlin入門', 'コトリン太郎', '1950-10-01'),
  (200, 'Java入門', 'ジャヴァ太郎', '2005-08-29');
  
insert into user values
  (1, 'admin@test.com', '$2a$10$LtjBeS5UXnqAlThTFHRvwu6LZfaRis6dTHwxBCwyftYUKOGE4rEiW', '管理者', 'ADMIN'),
  (2, 'user@test.com', '$2a$10$ISKdZYxTEDgwvH0EtOisPuRwn5GyLwlmkxkvybS/6aXNgMVWIWAHG', 'ユーザー', 'USER');
```

## コード生成
```bash
./gradlew mbGenerator
```
か、IntelliJ IDEA の Gradle タスクの mbGenerator を実行。
