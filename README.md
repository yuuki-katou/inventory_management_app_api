# 機能と特徴

## CRUD操作
CRUD操作は商品情報の登録、参照、更新、削除を可能にします。<br>
各操作には次のエンドポイントが用意されています。
| 操作       | HTTPメソッド | エンドポイント              | 説明                             |
|----------|-------------|--------------------------|---------------------------------|
| 登録（Create） | POST        | `localhost:8080/products` | 新規商品情報をデータベースに追加します。 |
| 読み取り（Read） | GET         | `localhost:8080/products` | 既存商品情報を一覧形式で取得します。      |
|            | GET         | `localhost:8080/products/{id}` | 特定商品の詳細情報を取得します。        |
| 更新（Update） | PATCH       | `localhost:8080/products/{id}` | 特定商品情報を更新します。              |
| 削除（Delete） | DELETE      | `localhost:8080/products/{id}` | 商品情報をデータベースから削除します。  |

## 検索・並び替え機能
商品情報の検索と並び替えが可能で、ユーザーのニーズに合わせた情報探索を可能にします。
| 機能         | HTTPメソッド | エンドポイント                     | 説明                                 |
|------------|-------------|---------------------------------|------------------------------------|
| 検索         | GET         | `localhost:8080/products/search` | キーワードやカテゴリ、ブランド、商品の状態、取り扱い店舗、価格範囲を指定して商品を検索します。       |
| 並び替え       | GET         | `localhost:8080/products/search` | 検索結果を新着順、更新順、価格の低い順、価格の高い順に並び替えます。       |

## アプリ起動時のデータ送信
アプリケーションの起動時に、フロントエンドに重要な参照データが送信されます。<br>
このデータはフロントエンドのドロップダウンメニューなどで使用され、ユーザーに最新の情報を提供します。
| データ種類     | HTTPメソッド | エンドポイント               | 説明                             |
|------------|-------------|---------------------------|---------------------------------|
| カテゴリ       | GET         | `localhost:8080/categories` | 利用可能なカテゴリ情報を提供します。           |
| ブランド       | GET         | `localhost:8080/brands`     | 利用可能なブランド情報を提供します。           |
| 商品の状態      | GET         | `localhost:8080/conditions` | 商品の状態に関する情報を提供します。           |
| 取り扱い店舗     | GET         | `localhost:8080/stores`     | 利用可能な店舗情報を提供します。               |

## バリデーションチェック
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

## ログ機能
アプリケーションの動作状況やエラーを記録し、問題解決に役立つ詳細なログ機能を提供します。
| 項目         | 説明                                    |
|------------|---------------------------------------|
| ログファイル    | `./logs/application.log`              |
| フォーマット   | タイムスタンプ、スレッド名、ログレベル、ロガー名、メッセージ |
| ローテーション | 時間とサイズに基づく、アーカイブは`./logs/archived/`に保存 |
| 保存期間      | アーカイブログは90日間保存、以降自動削除         |

## 例外ハンドリング
エラー発生時にユーザーに明確なフィードバックを提供し、問題解決をサポートする効果的な例外ハンドリング機能。
## 例外ハンドリング
アプリケーションでは、エラー発生時にユーザーに明確なフィードバックを提供し、問題解決をサポートするための詳細な例外ハンドリング機能が実装されています。
| エラー種類              | HTTPステータスコード | 説明                             | レスポンス内容                                         |
|----------------------|---------------------|---------------------------------|---------------------------------------------------|
| リソースが見つからない場合 | 404                 | 要求されたリソースが見つからないときに表示されます。 | エラー発生時のタイムスタンプ、HTTPステータスコード、エラーメッセージ、リクエストURI |
| 内部整合性エラー          | 500                 | システム内部でのエラー時に表示されます。              | エラー発生時のタイムスタンプ、HTTPステータスコード、エラーメッセージ、リクエストURI |
| バリデーションエラー        | 400                 | ユーザー入力が不適切な場合に表示されます。              | エラー発生時のタイムスタンプ、HTTPステータスコード、各入力フィールドのエラーメッセージ、リクエストURI |


