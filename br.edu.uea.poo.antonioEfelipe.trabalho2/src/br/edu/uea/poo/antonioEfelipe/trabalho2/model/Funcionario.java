package br.edu.uea.poo.antonioEfelipe.trabalho2.model;

public class Funcionario extends Usuario{
	private String email;
	private int senha;
	
	public Funcionario(String nomeFuncionario, String emailFuncionario, int senhaFuncionario) {
		setNome(nomeFuncionario);
		setEmail(emailFuncionario);
		setSenha(senhaFuncionario);
	}
	
	public int getSenha() {
		return senha;
	}
	public void setSenha(int senha) {
		this.senha = senha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean autenticar(int senha) {
		return this.senha == senha;
	}
	
}
