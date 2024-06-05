package br.com.api.cadastroCliente.dto;

import br.com.api.cadastroCliente.entities.Cliente;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class ClienteDTO {
    private Long id;
    @Size(min = 3, max = 80, message = "name precisa ter de 3 a 80 caracteres.")
    @NotBlank(message = "name é obrigatorio.")
    private String name;
    @Column(length = 11)
    @NotBlank(message = "CPF é obrigatorio.")
    @Pattern(regexp="[0-9]{11}", message="CPF deve conter exatamente 11 dígitos.")
    private String cpf;

    @Positive(message = "o income precisa ser positivo")
    private Double income; // renda

    @PastOrPresent(message = "a data de nascimento nao pode ser futura")
    private LocalDate birthDate; // Data de Nascimento

    @Positive(message = "children nao pode ser negativo")
    private Integer children;

    public ClienteDTO(Long id, String name, String cpf, Double income, LocalDate birthDate, Integer children) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.income = income;
        this.birthDate = birthDate;
        this.children = children;
    }

    public ClienteDTO(Cliente cliente) {
        id = cliente.getId();
        name = cliente.getName();
        cpf = cliente.getCpf();
        income = cliente.getIncome();
        birthDate = cliente.getBirthDate();
        children = cliente.getChildren();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public Double getIncome() {
        return income;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Integer getChildren() {
        return children;
    }
}
