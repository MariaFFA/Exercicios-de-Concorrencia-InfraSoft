# Exercicios de Concorrência - InfraSoft
Exercícios de Concorrência em Java realizados na disciplina de Infraestrutura de Comunicação

**Problema 1** \
SISTEMA BANCÁRIO\
O banco criou um sistema para compartilhar uma mesma conta entre os membros de uma família. Onde é possível fazer depósitos e saques, que podem acontecer simultaneamente. Deseja-se manter a uniformidade do saldo da conta, que por sua vez é utilizada por mais de um membro da família. Sua tarefa é criar duas operações (saque e depósito) que alteram uma variável chamada saldo e garantir que um cliente não consiga sacar mais do que a conta tem de saldo. 

Observações:
- Lembre-se de simular várias pessoas (threads) sacando e depositando simultaneamente na mesma conta.
- Crie uma thread para cada pessoa que deseja fazer uma operação.

**Problema 2**\
CONSTRUÇÃO DE UMA PONTE\
Uma ponte está sendo construída, mas o espaço da ponte será bem estreita, com apenas uma única faixa. Mas, os carros (threads) trafegam nas duas direções. Portanto, é necessário alguma forma de sincronizar para que os carros não colidam. Na prática, quando a ponte está vazia, um carro (da esquerda ou da direita) pode entrar nela. Uma vez que o carro entra na ponte, ele precisa atravessá-la e depois sair. Só então um próximo carro (da esquerda ou da direita) pode usar a ponte. Sua tarefa é implementar um programa que simula essa situação. Considere ainda duas versões desse mesmo problema, uma onde a ponte tem o controle de fluxo (com sincronização) e outra onde a ponte não tem controle de fluxo (sem sincronização).

Observações:
- Crie uma thread para cada carro.

**Problema 3**\
BARBEIRO DA CIDADE\
Uma barbearia consiste em uma sala de espera com n cadeiras e a sala do barbeiro contendo a cadeira do barbeiro. Se não houver clientes para atender, o barbeiro vai dormir. Se um cliente entra na barbearia e todas as cadeiras estão ocupadas, o cliente sai da loja. Se o barbeiro estiver ocupado, mas houver cadeiras disponíveis, o cliente senta-se em uma das cadeiras livres. Se o barbeiro estiver dormindo, o cliente acorda o barbeiro. Escreva um programa para coordenar o barbeiro e os clientes.

Observações:
- Dica: Dá pra simular o tempo de descanso do barbeiro utilizando o sleep.
- Crie uma thread para cada cliente.

**Problema 4**\
RESTAURANTE\
Um restaurante foi criado com apenas 5 lugares. Se um cliente chegar quando um dos lugares estiver vazio, ele/ela poderá sentar imediatamente. Mas, se o cliente chegar quando os cinco lugares estiverem ocupados, isto significa que os 5 clientes sentados estão jantando juntos e será preciso esperar todos os cinco clientes saírem antes que o próximo cliente possa sentar em um dos lugares.

Observações:
- Lembre-se que os clientes que não foram atendidos devem esperar numa fila.
- Crie uma thread para cada cliente.

**Problema 5**\
TRANSPORTE\
New City está evoluindo e deseja criar um novo sistema para os ônibus da cidade. Onde a filosofia é que ninguém deve ir em pé no ônibus, ou seja, quando o ônibus antige sua capacidade máxima de assentos, ninguém mais entra. Logo, você deve modelar um sistema para resolver o seguinte problema: Os passageiros chegam a um ponto de ônibus e esperam por um ônibus. Quando o ônibus chega, todos os passageiros tentam entrar, mas quem chega enquanto o ônibus está embarcando tem que esperar pelo próximo ônibus. A capacidade do ônibus é de 50 pessoas. Se houver mais de 50 pessoas esperando, algumas terão que esperar pelo próximo ônibus. Quando todos os passageiros em espera tiverem embarcado, o ônibus poderá partir. Se o ônibus chegar e estiver sem passageiros na parada, ele deverá partir imediatamente.

Observações:
- Repare que não é formada uma fila para entrar no ônibus, quando o ônibus chega, todos tentam entrar ao mesmo tempo até que as cadeiras esgotem. 
- Crie threads para cada passageiro.
- Simule que o tempo do ônibus passar na parada varia entre 1 e 3 segundos.

**Problema 6**\
BANHEIRO UNISEX\
Está sendo implantado um novo projeto de banheiros unisex espalhados pela cidade. Mas há algumas regras nesses banheiros e você deve garantir que essas regras sejam seguidas.
Não pode haver mulheres e homens no banheiro ao mesmo tempo.
A capacidade máxima do banheiro é de 3 pessoas.

Observações:
- Perceba que as pessoas podem demorar mais ou menos tempo dentro do banheiro.
- Perceba também que isso pode gerar um deadlock, então você deve garantir que isso não aconteça.
- Crie threads para cada pessoa.
