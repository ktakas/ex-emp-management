package jp.co.sample.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

/**
 * employeesテーブルを操作するリポジトリ.
 * 
 * @author kohei.takasaki
 */
@Repository
public class EmployeeRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = (rs, i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));
		return employee;
	};
	
	/**
	 * 従業員情報を入社日順（降順）で取得する.
	 * 
	 * @return 従業員のリスト（存在しない場合はサイズ0）
	 */
	public List<Employee> findAll() {
		String sql = "SELECT\r\n" + 
				"	*\r\n" + 
				"FROM\r\n" + 
				"	employees\r\n" + 
				"ORDER BY\r\n" +
				"	hire_date DESC" +
				";";
		List<Employee> employees = template.query(sql, EMPLOYEE_ROW_MAPPER);
		return employees;
	}
	
	/**
	 * 主キーから従業員情報を取得する.
	 * 
	 * @param id
	 * @return 従業員（存在しない場合にはnull）
	 */
	public Employee load(Integer id) {
		String sql = "SELECT\r\n" + 
				"	*\r\n" + 
				"FROM\r\n" + 
				"	employees\r\n" + 
				"WHERE\r\n" + 
				"	id=:id\r\n" + 
				";";
		SqlParameterSource param = new MapSqlParameterSource()
										.addValue("id", id);
		
		try {
			Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
			return employee;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 従業員情報を変更する.
	 * 
	 * @param employee 従業員
	 */
	public void update(Employee employee) {
		String sql = "UPDATE\r\n" + 
				"	employees\r\n" + 
				"SET\r\n" + 
				"	name=:name,\r\n" + 
				"	image=:image,\r\n" + 
				"	gender=:gender,\r\n" + 
				"	hire_date=:hireDate,\r\n" + 
				"	mail_address=:mailAddress,\r\n" + 
				"	zip_code=:zipCode,\r\n" + 
				"	address=:address,\r\n" + 
				"	telephone=:telephone,\r\n" + 
				"	salary=:salary,\r\n" + 
				"	characteristics=:characteristics,\r\n" + 
				"	dependents_count=:dependentsCount\r\n" + 
				"WHERE\r\n" + 
				"	id=:id\r\n" + 
				";";
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		int updatedNum = template.update(sql, param);
		System.out.println(updatedNum + "件のデータをアップデートしました");
	}
	
}
