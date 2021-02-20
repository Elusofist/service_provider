package elephant.mail.mail_sender.service;

import elephant.mail.mail_sender.model.Admin;
import elephant.mail.mail_sender.model.Expert;
import elephant.mail.mail_sender.repository.AdminDao;
import elephant.mail.mail_sender.service.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements UserDetailsService {
//    @Autowired
    private final AdminDao adminDao;
    @Autowired
    public AdminService(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        try {
            Optional<Admin> admin = adminDao.findByFirstName(name);
            if (!admin.isPresent()) {
                throw new UsernameNotFoundException(
                        "No admin found with name: " + name);
            }

            return new org.springframework.security.core.userdetails.User(
                    admin.get().getFirstName(),
                    admin.get().getPassword().toLowerCase(),
                    enabled,
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    admin.get().getAuthorities());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Admin> findByEmail(String email) {
        return adminDao.findByEmail(email);
    }

    public void addNewAdmin(Admin admin) throws BusinessException {
        System.out.println(admin.toString());
        if (adminDao.findByEmail(admin.getEmail()).isPresent())
            throw new BusinessException("این ایمیل قبلاً ثبت شده است.");
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminDao.save(admin);
    }
}
