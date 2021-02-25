# Aula 2 - Spring Data JPA

## Introdução
O *framework* Spring Data JPA atua na camada de persistência. Ele auxilia o programador na criação dos repositórios da aplicação. O projeto (Spring Data JPA) está dentro do Spring Data que possui diversos outros projetos que auxiliam no processo de acesso e persistência de dados. Sendo os principais projetos:
-   Spring Data Commons
-   Spring Data for Apache Cassandra
-   Spring Data Gemfire
-   Spring Data KeyValue
-   Spring Data LDAP
-   Spring Data MongoDB
-   Spring Data Redis
-   Spring Data REST

## Configurações
O projeto será criado utilizando como base o *framework* Spring Boot, que por sua vez permite que projetos com o Spring Data JPA já venham configurados por meio de convenções. 
Será criado um projeto [Maven](https://maven.apache.org/) por meio da ferramenta web [Spring Initializr](https://start.spring.io/) com as seguintes configurações:
O projeto será do tipo **Maven Project**.
A linguagem será **Java**.
A versão do Spring Boot será a versão estável atual na data de criação do projeto (**2.2.5**).
Os metadados do projeto são:
- Group: **br.edu.utfpr.pb.pw25s**
- Artifact: **aula2**
- Options: 
	- Packaging: **Jar** 	
	- Java: **11**.

Em dependências devem ser selecionados os *frameworks*:
- Spring Data JPA
- Spring Devtools
- PostgreSQL Driver (ou o driver do banco de sua preferência H2, MySQL, etc...)
- Lombok

O projeto está configurado e pode ser realizado o download do mesmo e importado na IDE. O conteúdo do arquivo `pom.xml` pode ser visualizado abaixo:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.4.3</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>br.edu.utfpr.pb</groupId>
    <artifactId>aula2</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>aula2</name>
    <description>Projeto aula1 da disciplina PW25S</description>
    <properties>
        <java.version>11</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```
Após importar o projeto é necessário realizar as configurações de conexão com o banco de dados no arquivo `application.properties`, será utilizado o banco de dados PostgreSQL, as configurações dos demais SGBDs estão comentadas: 
``` properties
#configuração da porta do servidor
server.port=8025

# estratégia utilizado para o DDL
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

#Banco Postgres
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.datasource.url=jdbc:postgresql://localhost:5432/pw25s_aula2
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driverClassName=org.postgresql.Driver

#Banco Mysql
#spring.datasource.url=jdbc:mysql://localhost:3306/pw25s_aula2?useSSL=false
#spring.datasource.username=root
#spring.datasource.password=root
#spring.jpa.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
#spring.datasource.driverClassName=com.mysql.jdbc.Driver

# Banco H2 + H2 Console
#spring.h2.console.enabled=true
#spring.datasource.url=jdbc:h2:mem:pw25s_aula2
#spring.datasource.driverClassName=org.h2.Driver
#spring.datasource.username=sa
#spring.datasource.password=
#spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
#http://localhost:8025/h2-console

#Banco HEROKU
#spring.jpa.database=POSTGRESQL
#spring.datasource.platform=postgres
#spring.datasource.url=jdbc:postgresql://Host:Port/DatabaseName?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory
#spring.datasource.username=User
#spring.datasource.password=Password
#spring.datasource.driver-class-name=org.postgresql.Driver

#spring.datasource.testWhileIdle=true
#spring.datasource.validationQuery=SELECT 1
#spring.datasource.timeBetweenEvictionRunsMillis=3600000
#spring.jpa.hibernate.naming-strategy=org.hibernate.cfg.ImprovedNamingStrategy
#spring.jpa.properties.jadira.usertype.autoRegisterUserTypes=true
```
## Models utilizados no projeto
Neste projeto serão persistidos dados em duas tabelas do banco de dados: Categoria e Produto. O mapeamento ORM foi realizado nas classes Categoria e Produto, que são apresentadas abaixo:
#### Classe Categoria
``` java
package br.edu.utfpr.pb.pw25s.aula2.model;
// imports ...
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Categoria implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 50, nullable = false)
	private String descricao;
	
}
```
#### Classe Produto
``` java
package br.edu.utfpr.pb.pw25s.aula2.model;
// imports ...
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
	
	@Column(name = "data_fabricacao", nullable = false)
	private LocalDate dataFabricacao;
}
```
Depois de configurar o `pom.xml` é necessário configurar a para iniciar a aplicação, neste projeto é a classe `Aula2Application` que está no pacote principal da aplicação. Abaixo está o código inicial da classe:
``` java
package br.edu.utfpr.pb.pw25s.aula2;
// imports ...
@SpringBootApplication
public class Aula2Application implements CommandLineRunner {
public static void main(String[] args) {
		SpringApplication.run(Aula2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
```

## Interface JpaRepository
Agora é necessário criar o repositório (ou um DAO) para realizar as operações CRUD. Durante o desenvolvimento de uma aplicação é comum o programador criar um DAO genérico com os métodos buscar, salvar (inserir e atualizar) e remover. A funcão da interface JpaRepository é exatamente essa fornecer os métodos básicos e avançados de CRUD. No projeto foram criadas as interfaces `CategoriaRepository` e `ProdutoRepository` no pacote repository.

#### CategoriaRepository 
```Java
package br.edu.utfpr.pb.pw25s.aula2.repository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
}
```
#### ProdutoRepository
```Java
package br.edu.utfpr.pb.pw25s.aula2.repository;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
}
```
Após criadas as interfaces elas já podem ser utilizadas, não sendo necessário implementá-las. Essa tarefa será realizada pelo próprio *framework* Spring Data JPA em tempo de execução. 
Para realizar os testes das interfaces criadas será utilizada a classe principal da aplicação  a `Aula2Application`. No código abaixo foi injetada as dependências das interfaces CategoriaRepository e ProdutoRepository por meio da anotação `@Autowired`. Foram criados dois métodos, o `salvarCategoria()` e o `salvarProduto()`, que recebem uma categoria e um produto, respectivamente. No método `salvarCategoria()` é chamado o método `save()` da interface `CategoriaRepository`, esse é método responsável por persistir uma categoria no banco de dados. A interface JpaRepository possui diversos outros métodos de CRUD, tais como o `findAll()` que retorna uma lista de objetos com todos os registros do banco de dados, o `findById()` que retorna apenas um objeto de acordo com o Id passado por parâmetro, o `delete()` que remove um objeto, o `deleteById()`, entre outros.

```Java
@SpringBootApplication
public class Aula2Application implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;

	public static void main(String[] args) {
		SpringApplication.run(Aula2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria c1 = new Categoria();
		c1.setDescricao("Categoria Teste");
		salvarCategoria(c1);

		c1.setDescricao("Games");
		salvarCategoria(c1);

		Categoria c2 = new Categoria();
		c2.setDescricao("Eletrônicos");
		salvarCategoria(c2);

		listarCategorias();

		// SALVAR PRODUTOS
		Produto p1 = new Produto();
		p1.setNome("Age of Empires");
		p1.setDescricao("Jogo desenvolvido de estratégia desenvolvido...");
		p1.setValor(99.9);
		p1.setCategoria(c1);
		p1.setDataFabricacao(LocalDate.now());
		salvarProduto(p1);
	}

	private void salvarCategoria(Categoria categoria) {
		this.categoriaRepository.save(categoria);
		System.out.println("Categoria salva com sucesso! \n " + categoria);
	}

	private void listarCategorias() {
		System.out.println("\n *********** LISTA DE CATEGORIAS ***********");
		this.categoriaRepository.findAll().forEach(c -> System.out.println(c));
		System.out.println("\n *********** LISTA DE CATEGORIAS ***********");
	}

	private void salvarProduto(Produto produto) {
		this.produtoRepository.save(produto);
		System.out.println("Produto salvo com sucesso! \n " + produto);
	}
}

```

## Realizando consultas 

Por padrão a interface `JpaRepository` já possui métodos para buscar todos os registros, um único registro, fazer buscas páginadas. Porém durante o desenvolvimento é comum a criação de consultas personalizadas no SGBD filtrando por algum dos campos. Para isso existem algumas maneiras de realizar essas consultas, que será listado nos próximos tópicos.

### Consultas na Assinatura dos métodos
A maneira mais simples de realizar consultas realizando a interface `JpaRepository` é utilizando a assinatura de métodos. Para exemplificar as consultas será utilizado como exemplo consultas realizadas na interface para persistência de produtos `ProdutoRepository`.

```Java
public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	// Retorna uma lista de Produtos filtrando pelo nome, entretanto nesse método o Like não utiliza '% %' por padrão, caso necessário, deve ser passado junto com o parâmetro do método.
	List<Produto> findByNomeLike(String nome);
	
	// Retorna uma lista de Produtos filtrando pelo nome OU descricao, a palavra-chave Contaiks vai gerar, na consulta sql, um Like com '% %'
	List<Produto> findByNomeContainsOrDescricaoContains(String nome, String descricao);
	
	// Retorna uma lista de Produtos filtrando pelo nome OU descricao, como no método anterior, mas agora Ordenando pelo valor do produto em ordem decrescente
	List<Produto> findByNomeContainsOrDescricaoContainsOrderByValorDesc(String nome, String descricao);
	
	// Retorna uma lista de Produtos filtrando por um valor maior ou igual ao valor passado para o método, ordenando pelo nome do produto em ordem decrescente
	List<Produto> findByValorGreaterThanEqualOrderByNomeDesc(Double valor);

	// Retorna uma lista de Produtos filtrando pelo nome da categoria
	// Obs.: como o campo descrição é um atributo da classe Categoria, primeiro deve ser informado o atributo Categoria da classe produto, depois o atributo Descricao da classe categoria.
	List<Produto> findByCategoriaDescricaoContains(String categoria);
    
    // Retorna uma lista de Produtos filtrando pelo id da categoria
	List<Produto> findByCategoriaId(Long id);
	
	// Retorna uma lista de Produtos filtrando por um intervalo de datas
	List<Produto> findByDataFabricacaoBetween(LocalDate dataIni, LocalDate dataFim);
}
```
Um exemplo de consulta está exemplificado graficamente na próxima imagem.
- `List<Produto> findByNomeContainsOrValorGreaterThanOrderByNomeDesc(String Nome, Double valor);`

![List<Produto> findByNomeContainsOrValorGreaterThanOrderByNomeDesc(String Nome, Double valor);](https://github.com/viniciuspegorini/pw25s-2020-1/blob/master/aulas/imagens/consulta_nome-valor.png)

### Consultas com JPQL

### Consultas com query Nativa


## Conclusão

## Referências
Spring Data JPA - Disponível em: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference

## Atividade
Criar um novo projeto realizar o mapeamento ORM das classes e criar os repositórios para:
 - Cidade {id, nome}
 - Autor {id, nome}
 - Editora {id, nome}
 - Genero {id, descricao}
 - Livro {id, titulo, EDITORA, GENERO, AUTOR, ano, isbn, CIDADE, valor}

  Consultas:

 - Retornar os Livros pesquisando por parte do nome do autor e ordenando
   por ano. 

 - Retornar os livros filtrando por ano.

 - Retornar os livros    filtrando    pela descrição do gênero e
   ordenando por ano. 

 - Retornar os    livros    filtrando a mesma string    pelo ISBN ou
   Título e ordenar por ano       

 - Retornar os livros com    valores    maiores que... 

 - Retornar os    livros com     valores entre A* e    B*    ordenados
   por ano
