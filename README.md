# 在庫管理API

## API概要
音楽関連商品の在庫管理に特化したRESTful APIを開発しました。<br>
このAPIでは、商品情報の登録、検索・参照、更新、削除を簡単に行うことができます。

## 作成背景
このAPIは、Webアプリケーション開発の基本的な知識と技術を学ぶ目的で作成されました。<br>
在庫管理システムを選んだのは、CRUD処理を総合的に理解するためです。

_音楽関連商品に特化したのは、特別な理由はなく、単に音楽が個人的な趣味であるためです。_

## 主な使用技術

- **Spring Boot**：JavaのWebアプリケーション開発を効率化するフレームワーク。
  
- **MySQL**：リレーショナルデータベース管理システム。

- **MyBatis**：O/R Mapper SQL文の実行結果とオブジェクトのマッピングを行う。

- **JUnit**: 単体テストを実施するためのテストフレームワーク。

- **DBRider**:DBテストを容易にするライブラリ。実際のデータと期待値の比較が可能。

- **Mockito**：モックフレームワーク、「モック」を作成して、テストケース内で特定の振る舞いを模倣できる。

- **Docker**: コンテナ仮想化プラットフォーム。

## アプリケーション概略図
<img src="https://github.com/yuuki-katou/inventory_management_app_api/assets/142807995/ff94fa90-f5f3-4c2b-b445-2a99312528c4" width="500" height="550" alt="アプリケーションの図">



## 機能と特徴

### CRUD操作
CRUD操作は商品情報の登録、参照、更新、削除を可能にします。<br>
各操作には次のエンドポイントが用意されています。
| 操作       | HTTPメソッド | エンドポイント              | 説明                             |
|----------|-------------|--------------------------|---------------------------------|
| 登録（Create） | POST        | `localhost:8080/products` | 新規商品情報をデータベースに追加します。 |
| 読み取り（Read） | GET         | `localhost:8080/products` | 既存商品情報を一覧形式で取得します。      |
|            | GET         | `localhost:8080/products/{id}` | 特定商品の詳細情報を取得します。        |
| 更新（Update） | PATCH       | `localhost:8080/products/{id}` | 特定商品情報を更新します。              |
| 削除（Delete） | DELETE      | `localhost:8080/products/{id}` | 商品情報をデータベースから削除します。  |

### 検索・並び替え機能
商品情報の検索と並び替えが可能で、ユーザーのニーズに合わせた情報探索を可能にします。
| 機能         | HTTPメソッド | エンドポイント                     | 説明                                 |
|------------|-------------|---------------------------------|------------------------------------|
| 検索         | GET         | `localhost:8080/products/search` | キーワードやカテゴリ、ブランド、商品の状態、取り扱い店舗、価格範囲を指定して商品を検索します。       |
| 並び替え       | GET         | `localhost:8080/products/search` | 検索結果を新着順、更新順、価格の低い順、価格の高い順に並び替えます。       |

### アプリ起動時のデータ送信
アプリケーションの起動時に、フロントエンドに重要な参照データが送信されます。<br>
このデータはフロントエンドのドロップダウンメニューなどで使用され、ユーザーに最新の情報を提供します。
| データ種類     | HTTPメソッド | エンドポイント               | 説明                             |
|------------|-------------|---------------------------|---------------------------------|
| カテゴリ       | GET         | `localhost:8080/categories` | 利用可能なカテゴリ情報を提供します。           |
| ブランド       | GET         | `localhost:8080/brands`     | 利用可能なブランド情報を提供します。           |
| 商品の状態      | GET         | `localhost:8080/conditions` | 商品の状態に関する情報を提供します。           |
| 取り扱い店舗     | GET         | `localhost:8080/stores`     | 利用可能な店舗情報を提供します。               |

### バリデーションチェック
入力データの正確性を保証し、データ品質を維持するための厳格なバリデーションチェックを実施します。
| 項目         | ルール                                                 |
|------------|------------------------------------------------------|
| 商品名       | 最大50文字、空欄不可                                      |
| ブランド名     | 最大25文字、空欄不可                                      |
| 型番         | 最大10文字、空欄不可                                      |
| カテゴリ       | 最大25文字、空欄不可                                      |
| 価格         | 0以上1,000,000以下、空欄不可                               |
| 在庫数        | 0以上、空欄不可                                           |
| 商品の状態      | 最大25文字、空欄不可                                      |
| 説明文       | 最大1000文字                                              |
| 店舗名       | 最大25文字、空欄不可                                      |

### ログ機能
商品の登録、更新、削除時のエラーを記録し、問題解決を支援します。
| 項目         | 説明                                    |
|------------|---------------------------------------|
| ログファイル    | `./logs/application.log`              |
| フォーマット   | タイムスタンプ、スレッド名、ログレベル、ロガー名、メッセージ |
| ローテーション | 時間とサイズに基づく、アーカイブは`./logs/archived/`に保存 |
| 保存期間      | アーカイブログは90日間保存、以降自動削除         |

### 例外ハンドリング
エラー発生時にユーザーにフィードバックを提供します。
| エラー種類              | HTTPステータスコード | 説明                             | レスポンス内容                                         |
|----------------------|---------------------|---------------------------------|---------------------------------------------------|
| リソースが見つからない場合 | 404                 | 要求されたリソースが見つからないときに表示されます。 | エラー発生時のタイムスタンプ、HTTPステータスコード、エラーメッセージ、リクエストURI |
| 内部整合性エラー          | 500                 | システム内部でのエラー時に表示されます。              | エラー発生時のタイムスタンプ、HTTPステータスコード、エラーメッセージ、リクエストURI |
| バリデーションエラー        | 400                 | ユーザー入力が不適切な場合に表示されます。              | エラー発生時のタイ



ムスタンプ、HTTPステータスコード、各入力フィールドのエラーメッセージ、リクエストURI |

## 使用イメージ
### 登録処理
デモとして商品「TEST GUITAR」を登録します。<br>
処理完了後、新着順に並べ替え、商品情報が正常に登録されていることを確認しています。
![登録処理動画01](https://github.com/yuuki-katou/inventory_management_app_api/assets/142807995/24e26700-5f10-4843-9f53-587350c05744)

### 参照処理
#### キーワード検索と並び替え
キーワード「エレキギター」に関連する商品を一覧で取得しています。
また並び替え機能を一通り試し、並びが期待している通りになるか確認しています。
![IMG_6820](https://github.com/yuuki-katou/inventory_management_app_api/assets/142807995/89018a6d-cafb-4366-b05e-b4ef5b66053b)

#### 詳細検索
詳細検索を使用して商品情報を絞り込みます。<br>
デモでは、カテゴリ：「アンプ」> ブランド：「ブランド1」> 価格下限：500,000　の順に設定して商品を絞り込んでいます。
 ![参照処理（詳細検索）](https://github.com/yuuki-katou/inventory_management_app_api/assets/142807995/36eea4cc-3f1b-44d0-8f56-405fd53e2057)

### 更新処理
登録処理で新規登録した「TEST GUITAR」の情報を更新します。<br>
今回は、商品名と説明文に「（更新済み）」という文字を追記しています。
![更新処理](https://github.com/yuuki-katou/inventory_management_app_api/assets/142807995/ceb49a7d-2ce6-475a-8917-c98bf3c955ce)




## テスト

### テスト概要
- 実施したテストの種類: 結合テストおよび単体テスト
- テストの目的: アプリケーションの主要機能が仕様通りに動作することを確認
- 使用したツール: JUnit, Mockito, DBRider,PostMan

### テスト結果の概要
- 実施したテストケースの数: 50件
- 成功したテストケースの数: 50件
- 失敗したテストケースの数: 0件

### 備考
- 結果の詳細は別途Excelファイルをご覧ください。<br>
[【テスト表】inventory_management_app.xlsx](https://github.com/yuuki-katou/inventory_management_app_api/files/13692267/inventory_management_app.xlsx)
- GitHub Actionsを使用したテストの実装は、後程行う予定です。

## 今後の展望
- クラウドへのデプロイ：AWSをベンダーとして予定しています。理由はベンダーの中で最も知見が豊富にある為です。
ソリューションアーキテクトの勉強と並行して進めます。

- ログイン機能とSpring Securityの導入: セキュリティを強化し、ユーザーの認証・認可を管理するために、Spring Securityを取り入れる予定です。

- GitHub Actionsの活用: Pull requestが行われた際に自動テストが実行されるように、GitHub Actionsを設定する予定です。

