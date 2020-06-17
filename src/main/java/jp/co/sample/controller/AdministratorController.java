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
 * 
 * @author kohei.takasaki
 *
 * 管理者情報を操作するサービス
 */
@Controller
@RequestMapping("/")
public class AdministratorController {

	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private HttpSession session;
	
	/**
	 * LoginFormをインスタンス化する
	 * 
	 * @return LoginForm
	 */
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}
	
	/**
	 * InsertAdministratorFormをインスタンス化する
	 * 
	 * @return InsertAdministratorForm
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}
	
	/**
	 * administrator/login.htmlにフォワードする
	 * 
	 * @return 管理者ログイン画面へのパス
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}
	
	/**
	 * administrator/insert.htmlにフォワードする
	 * 
	 * @return 管理者情報を登録する処理へのパス
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}
	
	/**
	 * 引数のフォームからAdministratorのインスタンスへ情報をコピーし、サービスへ渡す
	 * 
	 * @param form
	 * @return ログイン画面へのパス
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		administratorService.insert(administrator);
		return "redirect:/";
	}
	
	@RequestMapping("/login")
	public String login(LoginForm form, Model model) {
		String mailAddress = form.getMailAddress();
		String password = form.getPassword();
		Administrator administrator = administratorService.login(mailAddress, password);
		
		if (administrator == null) {
			model.addAttribute("errorMsg", "メールアドレスまたはパスワードが不正です。");
			
			/* TODO: 管理者がnullの場合にどうするか */
			return "administrator/login";
					
		} else {
			model.addAttribute("administratorName", administrator.getName());
			return "forward:/employee/showList";
		}
	}
	
}
