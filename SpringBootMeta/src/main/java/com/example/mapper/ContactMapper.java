package com.example.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.model.Contact;
public class ContactMapper implements RowMapper<Contact> {

	@Override
	public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
		Contact contact = new Contact();
		contact.setId(rs.getInt(1));
		contact.setName(rs.getString(2));
		contact.setMobile(rs.getString(3));
		contact.setEmailid(rs.getString(4));
		contact.setCategory(rs.getString(5));
		return contact;
	}

}
