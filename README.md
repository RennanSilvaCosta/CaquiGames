# CaquiGames
 CaquiGames software desenvolvido para o trabalho de PI (Projeto Integrador) do SENAC - Analise e Desenvolvimento de Sistemas.
 
## :bulb: Desafio :bulb:
### Desenvolver um software que faça:
 * Vendas;
 * Cadastro, atualização, exclusão e busca de clientes;
 * Cadastro, atualização, exclusão e busca de funcionarios;
 * Cadastro, atualização, exclusão e busca de produtos;
 * Relatorios analiticos e sinteticos.

### Venda
<span>
  <img src="https://media.giphy.com/media/gouRQGVmp2BFTnthfx/giphy.gif">
  <img src="https://media.giphy.com/media/4fus6gQl0r3OQYc0s5/giphy.gif">
</span>

Tela responsável por registrar vendas, separada em três etapas:
1. Seleção de produtos, é possível escolher os produtos e a quantidade de cada um, a cada escolha de um produto o valor total do pedido é atualizado.
2. Forma de pagamento, é possível escolher duas formas de pagamento, **DINHEIRO** ou **CARTÃO DE CRÉDITO.** Cada forma de pagamento tem sua perculiaridade.
    * **Dinheiro:** É possível alterar o "VALOR RECEBIDO" do cliente, inserir descontos, e o troco é automaticamente calculado.
    * **Cartão de crédito:** É possivel escolher a quantidade de parcelas e inserir desconto.
3. Finalização do pedido, é possivel vizualizar o resumo do pedido, e selecionar o cliente para qual está vendendo, e finalizar a venda.

### Tela Principal
<img src="https://user-images.githubusercontent.com/45303056/145719584-9a14d072-b8a8-4ecf-b2be-ec881fb953c9.png">
 
Tela responsável por exibir resumo do dia, como "Total vendido", "Total de pedidos" e "Produtos em estoque".
Também por exibir informações básicas do usuario logado (NOME, EMAIL, FOTO) e apresentar o menu de ações possíveis, como;
* Realizar vendas (VENDAS)
* Gerenciamento de clientes (CLIENTES)
* Gerenciamento de funcionarios (FUNCIONARIOS)
* Gerenciamento de produtos (PRODUTOS)
* Geração de relatorios analiticos e sinteticos (RELATORIOS)

### Gerenciamento de clientes
<img src="https://user-images.githubusercontent.com/45303056/145719661-634b0633-7ccb-4d37-8c63-399d9591d7d9.png">

Tela responsável pelo gerenciamento de clientes
* Busca de clientes por nome ou cpf.
* Registro de clientes, necessários dados de identificação como; nome, cpf, email, data de nascimento, telefone, CEP.
* Atualização de clientes, é possível alterar qualquer dado do cliente já registrado.
* Exclusão de clientes, é possível excluir qualquer cliente já registrado.

### Gerenciamento de funcionarios
<img src="https://user-images.githubusercontent.com/45303056/145719684-81f52fa8-212d-49f5-bfa7-3bc2a37052af.png">

Tela responsável pelo gerenciamento de funcionarios
* Busca de funcionarios por nome, cpf.
* Registro de funcionarios, necessários dados de identificação como; nome, cpf, email, data de nascimento, perfil (ADM, VENDEDOR), telefone, CEP.
* Atualização de funcionarios, é possível alterar qualquer dado do funcionario já registrado.
* Exclusão de funcionarios, é possível excluir qualquer funcionario já registrado.

### Gerenciamento de produtos
<img src="https://user-images.githubusercontent.com/45303056/145719711-e0b3676b-b6ea-4d30-96d4-e0853c9f5fd0.png">

Tela responsável pelo gerenciamento de produtos
* Busca de produtos por nome ou marca.
* Registro de produtos, necessários dados como; nome, marca, quantidade em estoque, categoria, valor.
* Atualização de produtos, é possível alterar qualquer dado do produto já registrado.
* Exclusão de produtos, é possível excluir qualquer produto já registrado.

### Relatorios
<img src="https://user-images.githubusercontent.com/45303056/145719737-ecc84e52-dfac-42c4-abf9-d8c11c3a092b.png">

Tela responsável pela geração de ralatorios
* Gerar relatorio sintetico por periodo.
* Gerar relatorio analitico por periodo.
[Exemplo de relatorio analitico](https://github.com/RennanSilvaCosta/CaquiGames/files/7699116/relatorio.pdf)

## :page_with_curl: Prototipação :page_with_curl:
* [Figma](https://www.figma.com/file/doN7ujEgpoFYABAtFg9wNq/CaquiGames?node-id=0%3A1)

## :hammer_and_wrench: Tecnologias utilizadas :hammer_and_wrench:

* CSS
* intellij IDEA
* Java
* JavaFX
* Jasper Reports
* Maven
* Mysql
* [ViaCEP](https://viacep.com.br/)

## :books: Bibliotecas utilizadas :books:

* AnimateFX 1.2.1
* Caelum 2.1.2
* Gson 2.8.8
* Hibernate 5.4.28.Final
* JFoenix 9.0.10
* JasperReports 6.17.0
