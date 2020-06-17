package jp.co.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

/**
 * 従業員情報を検索する処理を記述.
 * 
 * @author kohei.takasaki
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * 従業員情報更新フォームをインスタンス化する.
	 * 
	 * @return UpdateEmployeeForm
	 */
	@ModelAttribute
	public UpdateEmployeeForm setUpUpdateEmployeeForm() {
		return new UpdateEmployeeForm();
	}
	
	/**
	 * 従業員情報を全件取得する.
	 * 
	 * @param model　リクエストスコープ
	 * @return 従業員情報一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList = employeeService.showList();
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}
	
	/**
	 * 一致する従業員情報の詳細を取得する.
	 * 
	 * @param id 従業員ID
	 * @param model リクエストスコープ
	 * @return IDと一致する従業員オブジェクト
	 */
	@RequestMapping("showDetail")
	public String showDetail(String id, Model model) {
		int employeeId = Integer.parseInt(id);
		Employee employee = employeeService.showDetail(employeeId);
		model.addAttribute("employee", employee);
		return "employee/detail";
	}
	
	/**
	 * 従業員の詳細を更新する.
	 * 
	 * @param form フォーム
	 * @return 従業員一覧画面にリダイレクト
	 */
	@RequestMapping("/update")
	public String update(UpdateEmployeeForm form) {
		int employeeId = Integer.parseInt(form.getId());
		int dependentsCount = Integer.parseInt(form.getDependentsCount());
		
		Employee employee = employeeService.showDetail(employeeId);
		employee.setDependentsCount(dependentsCount);
		employeeService.update(employee);
		return "redirect:/employee/showList";
	}
}
