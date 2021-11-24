# **PWAssistant - English**
### English Translation provided via Google Translate

PWAssistant was a project started in 2017, with the aim of facilitating the administration of a Discord server and improving the user experience. The bot was opened to more servers and, when this message was written in July 2020, added to over 300 servers, making it available to over 50,000 users. PWAssistant is a chat bot, which responds and generates events such as receiving messages, registering new users, etc. The server in question is related to a video game and certain features are specific to that game.

# Associated Technologies and Frameworks

Java, Gradle, JDBC, MySQL, [JDA](https://github.com/DV8FromTheWorld/JDA), [LavaPlayer](https://github.com/sedmelluq/lavaplayer), [SLF4J](http:// www.slf4j.org) and Git.

# Hosting Service Used

Digital Ocean and Linux (Ubuntu).

# Functionalities

## Available to All Users

### Bot Information

* List available commands.
* Show information about a command: name, aliases, structure, description, how to use and usage examples.
* Show link to add the bot to a server.

### Fun

* Generating links to the lmgtfy site (let me google that for you) which indicates, ironically, that someone should have researched something online before asking.
* Generation of a random number between an upper and a lower limit.
* Repetition of something the user says.

### Utility

* Reception of new users on the main server - When a new user enters the main server, the bot welcomes them, indicates that they should read the rules and asks them questions to configure their experience on the server.
* Filter users connecting to the server - As a security measure, users are scanned when they join the server. Thus, it is possible to quickly find some users who violate critical server rules or who are a possible threat (such as bots that automatically promote content without permission).
* Assigning permissions to users - Users are allowed to add and remove certain roles (Discord roles) from themselves through commands. Thus, they can perform tasks such as managing subscriptions to notifications, making channels visible/hiding and exposing information about themselves to facilitate communication.
* Get link to a Google search.
* Information about the current server:
  * Name
  * Creation date
  * Server location
  * Icon
  * Owner
  * Number of emotes
  * Number of users distinguishing users, bots and status (online/offline)
  * Number of categories
  * Channel numbers distinguishing between voice and text channels
  * Default channel
  * Invitation to server (if possible)
* View information about the number of users distinguishing people, bots and status (online/offline).

### Match

* Discover game promo codes - Command that lists active promo codes. These codes are extracted from an online page with only text, making it easy to modify the list at any time through any device that has a browser and internet access.
* View schedule on different game servers.
* Check the status (online/offline) of game servers.
* Find information about game items.
* Find current information on the prices of in-game items, which players are selling them and where.

### Song

* Music playback on voice channels via YouTube links. Including:
  * Add a track to the playlist to play
  * Add a track to the playlist as the next one to play
  * Indicate which track is currently playing
  * View the list of tracks to play
  * Stop the current track and go to the next
  * Clear the track list
  * Increase or decrease the volume
  * Join or leave a voice channel

## Available to Server Administrators Only

* Scheduling notifications - messages that must be sent monthly or weekly on a specific day (such as the 12th or Monday).
* Trigger management - a trigger is a word that causes the bot to respond with a predefined message when found in a message.
* Creation of countdown clocks - clock updated every minute that shows the time left until the countdown runs out.
* Creation of a clock that shows the updated time on the different game servers. The clock is updated every minute.

## Available to Bot Owners Only

* Check the number of servers the bot is on, the number of users it has contact with and discriminate the number of online users.
* List all servers the bot is on and their IDs.
* Log out of a server given your ID.
* New user log - Whenever a new user enters the main server, a notification is sent along with account age information.
* Indicates when a user starts
respond to the welcome questionnaire.

# Settings

To run the bot you need to configure:
* Data base
* Bot account at https://discord.com/developers/applications
* Configuration files according to the template in the project's root directory
   * token.cfg
   * config.cfg
   * ItemIDList.cfg

---

# **PWAssistant - Portuguese**

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
