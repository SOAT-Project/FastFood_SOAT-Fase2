# FastFood SOAT

## Executando a aplicação com Docker

### Pré-requisitos
- [Docker](https://www.docker.com/get-started) instalado
- [Docker Compose](https://docs.docker.com/compose/install/) instalado

### Configuração do ambiente

Antes de iniciar a aplicação, é necessário configurar as variáveis de ambiente. Você tem duas opções:

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
```
- Windows (PowerShell):
```PowerShell
$env:APPLICATION_PORT="8080"
$env:DATABASE_HOST="localhost"
$env:DATABASE_PORT="5432"
$env:DATABASE_NAME="postgres"
$env:DATABASE_USER="postgres"
$env:DATABASE_PASS="P@ssw0rd"
```

Se nenhuma das opções acima for configurada, o Docker Compose utilizará os valores padrão definidos no arquivo `docker-compose.yaml`.

### Comandos Docker

#### Construir a imagem da aplicação
```bash
docker build -t fastfood-soat --build-arg APPLICATION_PORT=8080 .
```

#### Executar o container da aplicação
```bash
docker run --name fastfood-container -p 8080:8080 fastfood-soat
```

#### Parar o container
```bash
docker stop fastfood-container
```

### Utilizando Docker Compose

#### Iniciar todos os serviços
```bash
docker-compose up
```

#### Iniciar em modo detached (background)
```bash
docker-compose up -d
```

#### Construir os serviços antes de iniciar
```bash
docker-compose up --build
```

#### Parar todos os serviços
```bash
docker-compose down
```

#### Visualizar logs
```bash
docker-compose logs -f
```

### Estrutura do Docker Compose

O arquivo `docker-compose.yaml` configura os seguintes serviços:
- **application**: Aplicação principal (container name: application)
- **database**: Banco de dados (container name: database)

### Variáveis de ambiente

Você pode configurar as seguintes variáveis de ambiente:
- `DATABASE_HOST`: Host do banco de dados (padrão: localhost ou database no compose)
- `DATABASE_PORT`: Porta do banco de dados (padrão: 5432)
- `DATABASE_USER`: Usuário do banco de dados (padrão: postgres)
- `DATABASE_PASS`: Senha do banco de dados (padrão: P@ssw0rd)
- `DATABASE_NAME`: Nome do banco de dados (padrão: postgres)
- `APPLICATION_PORT`: Porta da aplicação (padrão: 8080, também usada como build-arg)

`