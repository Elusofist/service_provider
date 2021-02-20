package elephant.mail.mail_sender.service;

import elephant.mail.mail_sender.model.Customer;
import elephant.mail.mail_sender.model.Expert;
import elephant.mail.mail_sender.repository.CustomerDao;
import elephant.mail.mail_sender.service.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService implements UserDetailsService {
    private CustomerDao customerDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void addNewCustomer(Customer customer) throws BusinessException {
        if (customerDao.findByEmail(customer.getEmail()).isPresent())
            throw new BusinessException("این ایمیل قبلاً ثبت شده است.");
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerDao.save(customer);
    }

    public void updateCustomer(Customer customer) {
        customerDao.save(customer);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        try {
            Optional<Customer> customer = customerDao.findByEmail(email);
            if (!customer.isPresent()) {
                throw new UsernameNotFoundException(
                        "No customer found with email: " + email);
            }

            return new org.springframework.security.core.userdetails.User(
                    customer.get().getEmail(),
                    customer.get().getPassword().toLowerCase(),
                    customer.get().isEnabled(),
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    customer.get().getAuthorities());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Customer> findByEmailIgnoreCase(String email) {
        return customerDao.findByEmailIgnoreCase(email);
    }
}
