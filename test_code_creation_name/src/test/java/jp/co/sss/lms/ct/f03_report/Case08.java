package jp.co.sss.lms.ct.f03_report;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * 結合テスト レポート機能
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// トップページにアクセス
		goTo("http://localhost:8080/lms");

		// URL確認
		assertEquals("http://localhost:8080/lms/", webDriver.getCurrentUrl());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// ログインID・パスワード入力
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.id("password")).sendKeys("Studentaa01");

		// ログインボタン押下
		webDriver.findElement(By.xpath("//input[@type='submit' and @value='ログイン']")).click();

		// ページ読み込み待機
		pageLoadTimeout(5);

		// URL確認
		assertEquals("http://localhost:8080/lms/course/detail", webDriver.getCurrentUrl());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// テーブルの取得
		WebElement table = webDriver.findElement(By.className("sctionList"));
		List<WebElement> tableList = table.findElements(By.tagName("td"));
		// 提出状況の取得
		WebElement report = tableList.get(7);
		String reportStatus = report.getText();

		// 提出済みの場合のみ処理
		if (reportStatus.equals("提出済み")) {
			// 詳細ボタンをクリック
			tableList.get(9).click();

			// ページの一番下までスクロール
			scrollBy("document.body.scrollHeight");

			// ページを待機
			pageLoadTimeout(5);

			// 画面遷移先を確認
			assertEquals("http://localhost:8080/lms/section/detail", webDriver.getCurrentUrl());

			// 週報ボタンの表示確認
			WebElement weekReport = webDriver.findElement(By.xpath("//input[@value='提出済み週報【デモ】を確認する']"));
			assertEquals("提出済み週報【デモ】を確認する", weekReport.getAttribute("value"));

			// エビデンス取得
			getEvidence(new Object() {
			});
		}
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 「週報【デモ】を確認する」ボタン押下
		By reportCheckBtn = By.xpath("//input[@type='submit' and contains(@value,'週報【デモ】を確認する')]");
		visibilityTimeout(reportCheckBtn, 5);
		webDriver.findElement(reportCheckBtn).click();

		// ページ遷移待ち
		pageLoadTimeout(5);

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		// 目標の達成度
		By goalField = By.id("content_0");
		visibilityTimeout(goalField, 5);
		webDriver.findElement(goalField).clear();
		webDriver.findElement(goalField).sendKeys("2");

		// 所感
		By impressionField = By.id("content_1");
		visibilityTimeout(impressionField, 5);
		webDriver.findElement(impressionField).clear();
		webDriver.findElement(impressionField).sendKeys("所感のテスト");

		// 一週間の振り返り
		By reviewField = By.id("content_2");
		visibilityTimeout(reviewField, 5);
		webDriver.findElement(reviewField).clear();
		webDriver.findElement(reviewField).sendKeys("一週間のテスト");

		// エビデンス取得
		getEvidence(new Object() {
		}, "01");

		// 「提出する」ボタンをクリック
		By submitButton = By.xpath("//button[text()='提出する']");
		visibilityTimeout(submitButton, 5);
		webDriver.findElement(submitButton).click();

		// ページ遷移待ち
		pageLoadTimeout(5);

		// URL確認
		assertEquals("http://localhost:8080/lms/section/detail", webDriver.getCurrentUrl());

		// エビデンス取得
		getEvidence(new Object() {
		}, "02");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		// ヘッダー部「ようこそ受講生ＡＡ１さん」をクリック
		By userHeaderLink = By.xpath("//a[contains(text(),'ようこそ受講生ＡＡ１さん')]");
		visibilityTimeout(userHeaderLink, 5);
		webDriver.findElement(userHeaderLink).click();

		// ユーザー詳細画面が表示されることを確認
		assertEquals("http://localhost:8080/lms/user/detail", webDriver.getCurrentUrl());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		// 該当レポートの「詳細」ボタンをクリック
		By detailButton = By.xpath("//tr[.//span[contains(text(),'週報【デモ】')]]//input[@type='submit' and @value='詳細']");
		visibilityTimeout(detailButton, 5);
		webDriver.findElement(detailButton).click();

		// 週報【デモ】画面が表示されることを確認
		assertEquals("http://localhost:8080/lms/course/report/demo", webDriver.getCurrentUrl());

		// 内容の確認
		assertEquals("2", webDriver.findElement(By.name("goal")).getAttribute("value"));
		assertEquals("所感のテスト", webDriver.findElement(By.name("remarks")).getAttribute("value"));
		assertEquals("一週間のテスト", webDriver.findElement(By.name("weeklyReview")).getAttribute("value"));

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
