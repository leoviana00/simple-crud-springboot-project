<h1 align="center"> 🏗️ Arquitetura em Camadas em Aplicações Spring Boot </h1>

<p align="center">
  <img alt="Springboot" src="https://img.shields.io/static/v1?label=Backend&message=Springboot&color=8257E5&labelColor=000000"  />
  <img alt="License" src="https://img.shields.io/static/v1?label=license&message=MIT&color=49AA26&labelColor=000000">
</p>

<p align="center">
  <a href="#projeto">Projeto</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#dependências">Dependências</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#arquitetura">Arquitetura</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#build">Build</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#referências">Referências</a>
</p>

<p align="center">
  <img alt="Arquitetura" src="./data/arquitetura.svg">
</p>


## Projeto

- Basic CRUD project with Spring Boot to better understand its layer structure

## Dependências
- web
- jpa
- h2
- lombok

## Arquitetura

- Arquitetura em camadas:

A arquitetura em camadas separa as responsabilidades da aplicação em diferentes módulos, onde cada camada se comunica apenas com a imediatamente abaixo. Essa separação reduz o acoplamento e melhora a coesão do sistema.

➡️ **Principais camadas Spring Boot**

1️⃣ Camada de Apresentação (Controller)
- Responsável por receber requisições HTTP e retornar respostas. Atua como ponte entre o cliente e a lógica de negócio, mapea as rotas que o usuário pode acessar. Quando uma rota é acessada, a função correspondente é executada.
- Exemplos de mapeamento de rotas:
  - @GetMapping: Utilizada para buscar informações no banco (READ).
  - @PostMapping: Utilizada para adicionar novas informações ao banco (CREATE).
  - @DeleteMapping: Utilizada para remover informações do banco (DELETE).
  - @PutMapping: Utilizada para modificar dados existentes no banco (UPDATE).

```java
@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Void> salvarUsuario(@RequestBody Usuario usuario){
        usuarioService.salvarUsuario(usuario);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Usuario> buscarUsuarioPorEmail(@RequestParam String email){
        return ResponseEntity.ok(usuarioService.buscUsuarioPorEmail(email));
    }
}
```


2️⃣ Camada de Serviço (Service)
- Contém as regras de negócio da aplicação. Essa camada coordena operações e orquestra chamadas entre repositórios e outros serviços.

```java
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void salvarUsuario(Usuario usuario){
        usuarioRepository.saveAndFlush(usuario);
    }

    public Usuario buscUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Email não encontrado!")
        );
    }  
}
```

3️⃣ Camada de Persistência (Repository)
- Responsável por interagir com o banco de dados. Utiliza Spring Data JPA, JDBC, ou outras ferramentas de acesso a dados. A camada de Repository é dedicada exclusivamente à comunicação com o banco de dados. Criamos um repository que estende o JpaRepository para obter funcionalidades avançadas de forma simplificada.

```java
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    Optional<Usuario> findByEmail(String email);
}
```

4️⃣ Camada de Domínio (Model/Entity)
- Define as entidades de negócio com seus atributos e relacionamentos. Essas classes representam os dados da aplicação. Para mapear a classe como uma entidade no banco de dados, utilizamos @Entity. Para definir o nome da tabela, utilizamos @Table(name="nomeDaTabela").

```java
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "usuario")
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "nome")
    private String nome;

}
```

📦 Estrutura típica de pacotes

```bash
           └── viana.io.crud_spring_project_structure
               ├── business
               │   └── UsuarioService.java
               ├── controller
               │   └── UsuarioController.java
               └── infrastructure
                   ├── entitys
                   │   └── Usuario.java
                   └── repository
                       └── UsuarioRepository.java
```

✅ Benefícios da arquitetura em camadas
- ✔️ Clareza na separação de responsabilidades 
- ✔️ Melhor organização do código 
- ✔️ Facilidade na manutenção e testes 
- ✔️ Escalabilidade e reuso de componentes


💉 Injeção de Dependência

Uma camada não se comunica diretamente com outra sem um "caminho" definido. Para resolver isso, utilizamos a Injeção de Dependência, um padrão que permite que uma classe receba suas dependências de maneira externa.

- **Injeção de Dependência via Construtor**

```java
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
}
```
> **_NOTE:_** Essa abordagem melhora a testabilidade do código e evita dependências ocultas.

- **Injeção de Dependência via @Autowired**

```java
@Service
public class UsuarioService {

    @Autowired
    private final UsuarioRepository usuarioRepository;
}
```
> **_NOTE:_** Embora @Autowired seja uma opção válida, a injeção via construtor é mais recomendada.

_____________________________________________________

- 📌 Conclusão:

Adotar uma arquitetura em camadas bem definida em projetos Spring Boot é um passo fundamental para aplicações robustas, limpas e profissionais. 💡

🌱 Spring Boot oferece os componentes certos para implementar essa separação de forma simples e eficiente.

## Build

- Run 
```bash
mvn spring-boot:run
```

## Referências
- [Arquitetura do Spring Boot para Web Services!](https://www.youtube.com/watch?v=4g9EmpSBOYI)
- [CRUD Java com Spring Boot 2025 | Aprenda o Projeto QUE TODO DEV PRECISA TER no Portfólio!](https://www.youtube.com/watch?v=yW7RrWfUeHE&t=2181s)
