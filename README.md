# THIS VERSION IS OUTDATED
You can find the new version at https://github.com/ArthurSchiavom/PWAssistant-V3. V3 follows the new Discord policies for verified bots, which is mandatory for bots in over 100 servers. It was also built from scratch so it's using better frameworks and has a (much) cleaner code.

# PWAssistant

PWAssistant foi um projeto começado em 2017, com o objetivo de facilitar a administração de um servidor do Discord e melhorar a experiência dos utilizadores. O bot foi aberto para mais servidores e, aquando da escrita desta mensagem em Julho de 2020, adicionado a mais de 300 servidores, ficando disponível a mais de 50 mil utilizadores. PWAssistant é um bot de chat, que responde e gera eventos como receção de mensagens, registo de novos utilizadores, etc. O servidor em causa é relativo a um vídeo-jogo e certas funcionalidades são específicas a esse jogo.

# Tecnologias e Frameworks Associadas

Java, Gradle, JDBC, MySQL, [JDA](https://github.com/DV8FromTheWorld/JDA), [LavaPlayer](https://github.com/sedmelluq/lavaplayer), [SLF4J](http://www.slf4j.org) e Git.

# Serviço de Hosting Utilizado

Digital Ocean e Linux (Ubuntu).

# Funcionalidades

## Disponíveis a Todos os Utilizadores

### Informação Relativa ao Bot

* Listar os comandos disponíveis.
* Mostrar informação sobre um comando: nome, nomes alternativos, estrutura, descrição, como usar e exemplos de uso.
* Mostrar link para adicionar o bot a um servidor.

### Diversão

* Geração de links para o site lmgtfy (let me google that for you) que indica, de maneira irónica, que alguém deveria ter pesquisado algo online antes de perguntar.
* Geração de um número aleatório entre um limite superior e um inferior.
* Repetição de algo que o utilizador diga.

### Utilidade

* Receção de novos utilizadores no servidor principal - Quando um novo utilizador entra no servidor principal, o bot dá-lhes as boas vindas, indica que devem ler as regras e faz-lhes questões para configurar a sua experiência no servidor.
* Filtrar utilizadores que se conectam ao servidor - Como medida de segurança, os utilizadores são analisados quando se juntam ao servidor. Assim é possível rapidamente encontrar alguns utilizadores que violem regras critícas do servidor ou que sejam um possível ameaça (como bots que promovem conteúdo automaticamente sem permissão).
* Atribuição de permissões a utilizadores - Os utilizadores têm permissão para adicionar e remover certos papéis (Discord roles) de si mesmos através de comandos. Assim conseguem executar tarefas como gerir subscrições a notificações, tornar visível/esconder canais e expor informação sobre si para facilitar comunicação.
* Obter link para uma pesquisa no Google.
* Informação sobre o servidor atual: 
  * Nome
  * Data de criação
  * Localização do servidor
  * Ícone
  * Dono
  * Número de emotes
  * Número de utilizadores distinguindo utilizadores, bots e estado(online/offline)
  * Número de categorias
  * Números de canais distinguindo entre canais de voz e de texto
  * Canal default
  * Convite para o servidor (se possível obter)
* Visualizar informação sobre o número de utilizadores distinguindo pessoas, bots e estado(online/offline).

### Jogo

* Descobrir códigos promocionais do jogo - Comando que lista os códigos promocionais ativos. Estes códigos são extraídos de uma página online com apenas texto, facilitando a modificação da lista em qualquer momento através de qualquer dispositivo que tenha um browser e acesso à internet.
* Visualizar horário nos diferentes servidores do jogo.
* Verificar o estado (online/offline) dos servidores do jogo.
* Encontrar informação sobre itens do jogo.
* Encontrar informação atual sobre os preços de itens do jogo, que jogadores os estão a vender e onde.

### Música

* Reprodução de música nos canais de voz através de links do YouTube. Incluindo:
  * Adicionar uma faixa à lista de faixas a reproduzir
  * Adicionar uma faixa à lista de reprodução como sendo a próxima a reproduzir
  * Indicar qual faixa está a reproduzir no momento
  * Ver a lista de faixas a reproduzir
  * Parar a faixa atual e passar à seguinte
  * Limpar a lista de faixas
  * Aumentar ou diminuir o volume
  * Entrar ou sair de um canal de voz

## Disponíveis Apenas a Administradores do Servidor

* Agendamento de notificações - mensagens que devem ser enviadas mensalmente ou semanalmente num dia específico (como dia 12 ou segunda-feira).
* Gerenciamento de triggers - um trigger é uma palavra que faz com que o bot responda com uma mensagem pré-definida quando encontrada numa mensagem.
* Criação de relógios de contagem decrescente - relógio atualizado a cada minuto que mostra o tempo que falta até acabar a contagem.
* Criação de um relógio que mostra o horário atualizado nos diferentes servidores do jogo. O relógio é atualizado a cada minuto.

## Disponíveis Apenas a Donos do Bot

* Verificar o número de servidores em que o bot está, número de utilizadores com que tem contato e discriminar o número de utilizadores online.
* Listar todos os servidores em que o bot está e respetivos IDs.
* Sair de um servidor dado o seu ID.
* Log de novos utilizadores - Sempre que um novo utilizador entra no servidor principal, é enviada uma notificação juntamente com informação sobre a idade da conta.
* Indica quando um utilizador começa a responder ao questionário de boas vindas.

# Configuração

Para executar o bot é preciso configurar:
* Base de dados
* Conta de bot em https://discord.com/developers/applications
* Ficheiros de configuração de acordo com o template no diretório root do projeto
  * token.cfg
  * config.cfg
  * ItemIDList.cfg
