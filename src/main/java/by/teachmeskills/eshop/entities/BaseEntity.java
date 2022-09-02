package by.teachmeskills.eshop.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.opencsv.bean.CsvBindByName;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public class BaseEntity {
    @Id
    @Column(name = "id")
    @CsvBindByName(column = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
}