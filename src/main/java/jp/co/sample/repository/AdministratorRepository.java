package jp.co.sample.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;

/**
 * administratorsテーブルを操作するリポジトリ.
 * 
 * @author kohei.takasaki
 */
@Repository
public class AdministratorRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs, i) -> {
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mail_address"));
		administrator.setPassword(rs.getString("password"));
		return administrator;
	};
	
	
	/**
	 * 管理者情報を挿入する.
	 * 
	 * @param administrator 管理者情報
	 */
	public void insert(Administrator administrator) {
		String sql = "INSERT INTO\r\n" + 
				"	administrators(name, mail_address, password)\r\n" + 
				"VALUES\r\n" + 
				"	(:name, :mailAddress, :password)\r\n" + 
				";";
		SqlParameterSource param = new MapSqlParameterSource()
									.addValue("name", administrator.getName())
									.addValue("mailAddress", administrator.getMailAddress())
									.addValue("password", administrator.getPassword());
		Integer insertedNum = template.update(sql, param);
		System.out.println(insertedNum + "件のデータをインサートしました");
	}
	
	/**
	 * メールアドレスとパスワードから管理者情報を取得する.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password パスワード
	 * @return 存在する場合は 管理者情報　 しない場合null
	 */
	public Administrator findByMailAddressAndPassword(
			String mailAddress,
			String password
	) {
		String sql = "SELECT\r\n" + 
				"	*\r\n" + 
				"FROM \r\n" + 
				"	administrators\r\n" + 
				"WHERE\r\n" + 
				"	mail_address = :mailAddress AND\r\n" + 
				"	password = :password\r\n" + 
				";";
		SqlParameterSource param = new MapSqlParameterSource()
											.addValue("mailAddress", mailAddress)
											.addValue("password", password);
		
		try {
			Administrator administrator = template.queryForObject(sql, param, ADMINISTRATOR_ROW_MAPPER);
			return administrator;
		} catch (Exception e) {
			return null;
		}
	}
	
}
