package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;

/**
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// 「機能」メニューをクリック
		webDriver.findElement(By.xpath("//a[contains(text(),'機能')]")).click();

		// 「ヘルプ」リンクをクリック
		webDriver.findElement(By.xpath("//a[contains(text(),'ヘルプ')]")).click();

		// URL確認
		assertEquals("http://localhost:8080/lms/help", webDriver.getCurrentUrl());

		// エビデンス取得
		getEvidence(new Object() {
		});

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// 「ヘルプ画面」を保持
		String helpHandle = webDriver.getWindowHandle();

		// 「よくある質問」リンクをクリック
		webDriver.findElement(By.xpath("//a[contains(text(),'よくある質問')]")).click();

		// 新しいタブに切り替え 
		ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
		tabs.remove(helpHandle);
		webDriver.switchTo().window(tabs.get(0));

		// URL確認
		assertEquals("http://localhost:8080/lms/faq", webDriver.getCurrentUrl());

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

}
