# 📦 FastFood API — Deploy no Docker & Docker Compose

Este projeto contém o Dockerfile necessário para gerar a imagem da API FastFood e utiliza-lá em containers, assim como 
um docker-compose.yaml para execução dessa imagem em conjunto com uma instância do Postgres.

✅ Passos para utilização do Docker e Docker Compose:

## Pré-requisitos
- [Docker](https://www.docker.com/get-started) instalado
- [Docker Compose](https://docs.docker.com/compose/install/) instalado

## 1. Docker

### 1.1 Construir a imagem da aplicação
Execute o seguinte comando na raiz do projeto para construir a imagem da aplicação FastFood API:
```bash
docker build -t fastfood-soat --build-arg APPLICATION_PORT=8080 .
```

### 1.2 Executar o container utilizando a imagem da aplicação
Após a construção da imagem, execute o seguinte comando para iniciar um container utilizando a imagem criada:
```bash
docker run --name fastfood-container -p 8080:8080 fastfood-soat
```

### 1.3 Parando a execução do container
Com o container em execução, você pode pará-lo utilizando o seguinte comando:
```bash
docker stop fastfood-container
```

## 2. Docker Compose

### 2.1 Configurar variáveis de ambiente

#### Opção 1: Usando arquivo .env
Crie um arquivo `.env` na raiz do projeto com as variáveis de ambiente. Você pode usar o arquivo `.env.example` como modelo:

```bash
cp .env.example .env
```

Em seguida, edite o arquivo `.env` e configure os valores apropriados para o seu ambiente, exemplo:

```
APPLICATION_PORT=8080
DATABASE_HOST=localhost
DATABASE_PORT=5432
DATABASE_NAME=postgres
DATABASE_USER=postgres
DATABASE_PASS=P@ssw0rd
AUTH_TOKEN_EXPIRATION=43200
MP_TOKEN=APP_USR-2512049377508546-052123-386869c4214628b0e44f44f638bc2ebe-2448858150
COLLECTOR_ID=2448858150
POS_ID=SUC001POS001
```

#### Opção 2: Usando variáveis de ambiente do sistema
Se não existir um arquivo `.env`, o Docker Compose utilizará as variáveis de ambiente definidas no sistema operacional. Você pode defini-las antes de executar os comandos do Docker Compose:

- Linux/macOS:
```bash
export APPLICATION_PORT=8080
export DATABASE_HOST=localhost
export DATABASE_PORT=5432
export DATABASE_NAME=postgres
export DATABASE_USER=postgres
export DATABASE_PASS=P@ssw0rd
export AUTH_TOKEN_EXPIRATION=43200
export MP_TOKEN=APP_USR-2512049377508546-052123-386869c4214628b0e44f44f638bc2ebe-2448858150
export COLLECTOR_ID=2448858150
export POS_ID=SUC001POS001
```
- Windows (PowerShell):
```PowerShell
$env:APPLICATION_PORT="8080"
$env:DATABASE_HOST="localhost"
$env:DATABASE_PORT="5432"
$env:DATABASE_NAME="postgres"
$env:DATABASE_USER="postgres"
$env:DATABASE_PASS="P@ssw0rd"
$env:AUTH_TOKEN_EXPIRATION="43200"
$env:MP_TOKEN="APP_USR-2512049377508546-052123-386869c4214628b0e44f44f638bc2ebe-2448858150"
$env:COLLECTOR_ID="2448858150"
$env:POS_ID="SUC001POS001"
```
Se nenhuma das opções acima for configurada, o Docker Compose utilizará os valores padrão definidos no arquivo `docker-compose.yaml`.

### 2.2 Iniciar todos os serviços
Após ter realizado a construção da imagem (passo 1.1), você pode iniciar todos os serviços definidos no `docker-compose.yaml` com o seguinte comando:
```bash
docker-compose up
```

### 2.3 Iniciar em modo detached (background)
Como alternativa ao passo 2.1, você pode iniciar os serviços em modo detached (em segundo plano) utilizando o seguinte comando:
```bash
docker-compose up -d
```

### 2.4 Iniciar todos os serviços realizando uma nova construção da imagem da aplicação (recomendado)
Como alternativa aos passos 2.1 e 2.2, você pode iniciar os serviços realizando uma nova construção da imagem da aplicação com o seguinte comando:
```bash
docker-compose up --build
```

### 2.5 Finalizar todos os serviços
Para finalizar todos os serviços iniciados pelo Docker Compose, utilize o seguinte comando:
```bash
docker-compose down
```

### 2.6 Visualizar logs dos serviços
```bash
docker-compose logs -f
```
