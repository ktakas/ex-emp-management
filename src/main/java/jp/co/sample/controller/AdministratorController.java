package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

/**
 * 管理者情報を操作するサービス.
 * 
 * @author kohei.takasaki
 */
@Controller
@RequestMapping("/")
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private HttpSession session;
	
	/**
	 * ログインフォームをインスタンス化する.
	 * 
	 * @return LoginForm
	 */
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}
	
	/**
	 * 登録フォームをインスタンス化する.
	 * 
	 * @return InsertAdministratorForm
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}
	
	/**
	 * ログイン画面にフォワードする.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}
	
	/**
	 * 登録画面ににフォワードする.
	 * 
	 * @return 管理者情報登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}
	
	/**
	 * 管理者情報を登録する.
	 * 
	 * @param form フォーム
	 * @return ログイン画面
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		administratorService.insert(administrator);
		return "redirect:/";
	}
	
	/**
	 * フォームからメールアドレスとパスワードを受け取りサービスから管理者オブジェクトを取得する.
	 * 
	 * @param form フォーム
	 * @param model リクエストスコープ
	 * @return 成功時は従業員一覧、失敗時はログイン画面のパス
	 */
	@RequestMapping("/login")
	public String login(LoginForm form, Model model) {
		String mailAddress = form.getMailAddress();
		String password = form.getPassword();
		Administrator administrator = administratorService.login(mailAddress, password);
		
		if (administrator == null) {
			model.addAttribute("errorMsg", "メールアドレスまたはパスワードが不正です。");
			return "administrator/login";		
		} else {
			model.addAttribute("administratorName", administrator.getName());
			return "forward:/employee/showList";
		}
	}
	
}
