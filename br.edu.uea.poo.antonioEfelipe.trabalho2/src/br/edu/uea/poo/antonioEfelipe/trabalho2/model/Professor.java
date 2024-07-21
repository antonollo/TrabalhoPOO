package br.edu.uea.poo.antonioEfelipe.trabalho2.model;

public class Professor extends Usuario{
	public boolean autenticar(int senha) {
		return this.getSenha() == senha;
	}
	
}
