package br.edu.uea.poo.antonioEfelipe.trabalho2.model;

public abstract class Usuario{
	private String nome;
	private String email;
	private int senha;
	abstract boolean autenticar (int senha);
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getSenha() {
		return senha;
	}
	public void setSenha(int senha) {
		this.senha = senha;
	}
	
}
