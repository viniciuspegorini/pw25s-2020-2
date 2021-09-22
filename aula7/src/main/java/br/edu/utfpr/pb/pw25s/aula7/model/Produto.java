package br.edu.utfpr.pb.pw25s.aula7.model;

import lombok.*;

import javax.persistence.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Base64;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Produto implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 100, nullable = false)
	private String nome;
	
	@Column(length = 1024, nullable = false)
	private String descricao;
	
	@Column(nullable = false)
	private Double valor; 
	
	@ManyToOne
	@JoinColumn(name = "categoria_id", referencedColumnName = "id")
	private Categoria categoria;

	@ManyToOne
	@JoinColumn(name = "marca_id", referencedColumnName = "id")
	private Marca marca;

	@Column(name = "imagem", length = 10)
	private String imagem; // apenas o nome da imagem com extensão

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "produto")
	private List<ImagemProduto> imagemProdutos;

	@Lob
	@Column(name="imagemb64")
	private byte[] imagemB64;

	@Column(name = "extensao_imagemb64", length = 20)
	private String extensaoImagemb64;

	public String getImagemB64(){
		return (this.imagemB64 != null ? new String(this.imagemB64) : null);
	}

}
