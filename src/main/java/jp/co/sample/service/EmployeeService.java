package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Employee;
import jp.co.sample.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	/**
	 * 従業員のリポジトリを呼び全従業員情報を取得する.
	 * 
	 * @return 全従業員のリスト
	 */
	public List<Employee> showList() {
		return employeeRepository.findAll();
	}
	
	/**
	 * 従業員情報を取得する.
	 * 
	 * @param id 従業員ID
	 * @return 従業員（存在しない場合にはnull）
	 */
	public Employee showDetail(Integer id) {
		return employeeRepository.load(id);
	}
	
}
