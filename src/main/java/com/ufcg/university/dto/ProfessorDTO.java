package com.ufcg.university.dto;

import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.*;

@Schema(name = "ProfessorDTO",
		description = "Modelo de ProfessorDTO da aplicação.",
		extensions = {
			@Extension(
				name = "myExt",
				properties = {
					@ExtensionProperty(
						name = "varA",
						value = "A"),
					@ExtensionProperty(
						name = "varB",
						value = "B")
				}
			)
		})
public class ProfessorDTO {

	@Schema(
			title = "Nome",
			description = "Nome do professor",
			example = "Pedro",
			required = true,
			extensions = {
					@Extension(
							name = "name",
							properties = @ExtensionProperty(
									name = "NameExtensionProperty-ProfessorDTO",
									value = "ValueNameExtensionProperty-ProfessorDTO",
									parseValue = true
							)
					)
			}
	)
	@NotBlank(message ="Invalid Name")
	@NotNull(message ="Invalid Name")
	@Pattern(regexp = "^\\p{Alpha}+$", message ="Invalid Name")
	private String name;

	@Schema(
			title = "Senha",
			description = "Senha",
			example = "1234Abcd",
			required = true,
			minLength = 8,
			maxLength = 16,
			format = "regex[0-9A-Za-z]"
	)
	@Size(min = 8, max = 16)
	@NotBlank(message ="Invalid Password")
	@NotNull(message ="Invalid Password")
	@Pattern(regexp = "^\\p{Alnum}+$", message ="Invalid Password")
	private String password;

	@Schema(
			title = "Tempo de Serviço",
			example = "15",
			minimum = "0",
			maximum = "70",
			exclusiveMinimum = true
	)
	@Min(0)
	@Max(70)
	@NotNull(message ="Invalid Service Time")
	private Integer serviceTime;

	@Schema(
			title = "Disciplina",
			example = "Lógica",
			nullable = true,
			allowableValues = "Cálculo 1, P1, LP1, EDA, Lógica"
	)
	@NotBlank(message = "Invalid Discipline")
	@NotNull(message = "Invalid Discipline")
	@Pattern(regexp = "Cálculo 1|P1|LP1|EDA|Lógica", message = "Invalid Discipline")
	private String discipline;
	
	public ProfessorDTO() {}

	public ProfessorDTO(String name, String password, Integer serviceTime, String discipline) {
		this.name = name;
		this.password = password;
		this.serviceTime = serviceTime;
		this.discipline = discipline;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(Integer serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getDiscipline() {
		return discipline;
	}

	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

