# Tabelas Hash — Amazon Books Reviews

Trabalho prático de Estruturas de Dados (RA3), desenvolvido em Java. O programa lê avaliações de livros da Amazon e identifica, para cada usuário, o livro com a maior média global entre aqueles que ele avaliou.

## O que o programa faz

- Lê o arquivo `Books_rating.csv` e descarta registros sem usuário, sem título ou com nota inválida.
- Guarda, em uma tabela hash própria, a soma das notas e a quantidade de avaliações de cada título.
- Guarda os usuários, seus nomes de perfil e os livros avaliados.
- Calcula o melhor livro de cada usuário usando a média global do título.
- Mostra os 10 livros de maior média com pelo menos 100 avaliações.
- Gera um CSV com o melhor livro de cada usuário e outro CSV em ordem decrescente de média.
- Consulta usuários informados no arquivo `user_id.txt`.
- Compara três funções hash: FNV1a, Polinomial 31 e djb2.

## Estruturas e decisões

As tabelas hash têm capacidade fixa de **1.000.003 posições** e usam **encadeamento separado** para tratar colisões, por meio de listas encadeadas implementadas no projeto.

A média de cada livro considera todas as avaliações válidas do título na base. Em caso de empate na escolha do melhor livro de um usuário, o programa prefere o livro com mais avaliações e, depois, o título em ordem alfabética. Se o usuário avaliou o mesmo título mais de uma vez, sua maior nota nesse título é usada na saída.

Para a lista ordenada de usuários, foi implementado **Merge Sort**. O desempate nessa lista usa maior quantidade de avaliações do livro e, depois, `User_id` em ordem alfabética.

## Arquivos necessários

- `Books_rating.csv`: base **Amazon Books Reviews**, disponível no Kaggle.
- `user_id.txt`: arquivo de texto com um `User_id` por linha para as consultas.

Esses dois arquivos devem estar na **pasta configurada como Working directory** no IntelliJ. Os arquivos de dados grandes não são incluídos diretamente no repositório.

## Como compilar e executar no IntelliJ IDEA

1. Abra o projeto no IntelliJ IDEA.
2. Confirme que as classes Java estão na pasta `src`.
3. Configure um JDK para o projeto em **File > Project Structure > Project**.
4. Selecione **Build > Build Project** para compilar.
5. Abra `src/Principal.java`.
6. Clique no triângulo verde ao lado do método `main` e escolha **Run 'Principal.main()'**.
7. Em **Run > Edit Configurations**, confira se o campo **Working directory** aponta para a pasta que contém `Books_rating.csv` e `user_id.txt`.
8. Veja os resultados na janela **Run**. Ao final, o programa imprime `Programa finalizado!`.

Para testar apenas as funções hash, abra `src/TesteFuncoesHash.java` e execute seu método `main` pelo triângulo verde.

## Arquivos gerados

Os arquivos são salvos na pasta definida em **Working directory**:

- `usuario_melhor_livro.csv`
- `usuario_melhor_livro_ordenado.csv`

Ambos contêm as colunas `User_id`, `profileName`, `Title`, `media_livro`, `N_livro` e `nota_usuario`.

## Resultados da execução com a base completa

Na execução realizada em 22/11/2026:

| Informação | Resultado |
| --- | ---: |
| Avaliações lidas | 3.000.000 |
| Avaliações válidas | 2.438.018 |
| Registros descartados | 561.982 |
| Usuários distintos | 1.008.961 |
| Títulos distintos | 206.711 |
| Linhas do CSV de usuários | 1.008.961 |
| Linhas do CSV ordenado | 1.008.961 |

Os tempos e as contagens de colisões e sondagens são exibidos na execução e analisados no relatório. Os tempos podem variar conforme o computador.

## Repositório e apresentação

- **Código no GitHub:** https://github.com/NicoleFutikami/tabelas-hash-amazon-books
- **Vídeo de apresentação:** adicionar o link após a gravação.
- **Relatório:** disponibilizar conforme a orientação de entrega do professor.

> Observação: a base de dados completa e os arquivos CSV grandes devem ser entregues ou compartilhados conforme as instruções do professor; eles não são publicados diretamente neste repositório.
