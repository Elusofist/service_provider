package elephant.mail.mail_sender.service;

import elephant.mail.mail_sender.model.Customer;
import elephant.mail.mail_sender.model.Expert;
import elephant.mail.mail_sender.repository.ExpertDao;
import elephant.mail.mail_sender.service.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExpertService implements UserDetailsService {
    private final ExpertDao expertDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ExpertService(ExpertDao expertDao) {
        this.expertDao = expertDao;
    }

    public void register(Expert expert) {
        expertDao.save(expert);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        try {
            Optional<Expert> expert = expertDao.findByEmail(email);
            if (!expert.isPresent()) {
                throw new UsernameNotFoundException(
                        "No expert found with email: " + email);
            }

            return new org.springframework.security.core.userdetails.User(
                    expert.get().getEmail(),
                    expert.get().getPassword().toLowerCase(),
                    expert.get().isEnabled(),
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    expert.get().getAuthorities());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addNewExpert(Expert expert) throws BusinessException {
        if (expertDao.findByEmail(expert.getEmail()).isPresent())
            throw new BusinessException("این ایمیل قبلاً ثبت شده است.");
        expert.setPassword(passwordEncoder.encode(expert.getPassword()));
        expertDao.save(expert);
    }

    public Optional<Expert> findByEmailIgnoreCase(String email) {
        return expertDao.findByEmail(email);
    }

    public void updateExpert(Expert expert) {
        expertDao.save(expert);
    }
}
