package by.teachmeskills.eshop.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Column(name = "login")
    @Size(min = 6, max = 30, message = "Login must be between 6 and 30 characters")
    @Pattern(regexp = "\\S+", message = "Spaces are not allowed")
    private String login;
    @Size(min = 6, max = 30, message = "Password must be between 6 and 30 characters")
    @Pattern(regexp = "\\S+", message = "Spaces are not allowed")
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_date")
    private LocalDate dateBorn;
    @Column(name = "email")
    private String eMail;
    @Column(name = "balance")
    private BigDecimal balance;
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Order> order;

    public User(int id, String login, String password, String name, String surname, LocalDate dateBorn, String eMail, BigDecimal balance) {
        super(id);
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.dateBorn = dateBorn;
        this.eMail = eMail;
        this.balance = balance;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, String name, String surname, LocalDate dateBorn, String eMail, BigDecimal balance) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.dateBorn = dateBorn;
        this.eMail = eMail;
        this.balance = balance;
    }

    public boolean checkFieldsNotNull(User user) {
        boolean flag = true;
        if (user.getLogin().isEmpty() ||
                user.getPassword().isEmpty() ||
                user.getName().isEmpty() ||
                user.getSurname().isEmpty() ||
                user.getEMail().isEmpty() ||
                user.getDateBorn() == null) {
            flag = false;
        }
        return flag;
    }
}