# Criando um Banco Digital com Java e Orientação a Objetos

Criado para o desafio da DIO.me!!!!!!

## Coisas que eu mudei:

- [X] Criei o arquivo `.gitignore` para remover os arquivos `.bin`;
- [X] Mudei do pacote padrão;
- [X] Adicionei testes de unidade;
- [X] Troquei os tipos monetários de `double` para um tipo `BigDecimal`;
- [X] Troquei a função `imprimirExtrato` para outra função que retorna esse resultado como String, para 
      ficar mais fácil de testar;
- [X] Adicionei funções lançamentos no extrato;      
- [X] Criei uma exceção quando o saque/transferência for maior que o limite;
- [X] Troquei os contrutores por contrutores estáticos (preferência pessoal);
- [X] As funções de impressão de extrato poderiam ser movidas para as superclasses / métodos default
      de interface. Eu fiz isso;
- [X] Troquei as propriedades “read-only” como `final`;
- [X] Adicionei rendimento mensal na classe `ContaPoupança`;
- [X] Usei algumas funções `default` em interfaces;
- [X] Movi as funções de cálculo de juros para a classe `Real`;
- [X] Adicionei `javadoc`;
- [X] Adicionei uma classe `ContaEspecial` que possui um limite de saque além do saldo.


