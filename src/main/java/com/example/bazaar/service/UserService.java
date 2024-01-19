package com.example.bazaar.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.example.bazaar.entity.User;
import com.example.bazaar.repository.UserRepository;
import com.example.bazaar.util.PasswordUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByUserName(username);
		if (!userOptional.isPresent()) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		User user = userOptional.get();
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	public void create(User request) {
		request.setPassword(PasswordUtil.getEncryptedPassword(request.getPassword()));
		userRepository.save(request);
	}

	public Optional<User> getById(Integer id) {
		return userRepository.findById(id);
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public void update(User request) {
		userRepository.saveAndFlush(request);
	}

	public void delete(Integer id) {
		userRepository.deleteById(id);
	}

	public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
		String filepath = "C:\\springboot project\\sales\\src\\main\\resources\\sales.jrxml";
		String path = "C:\\springboot project\\sales";
		List<User> user = userRepository.findAll();

		File file = ResourceUtils.getFile(filepath);

		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(user);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("createdBy", "Dhivya");

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

		if (reportFormat.equalsIgnoreCase("html")) {
			JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\sales.html");
		}
		if (reportFormat.equalsIgnoreCase("pdf")) {
			JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\sales.pdf");
		}

		return "report generated in path : " + path;
	}

}