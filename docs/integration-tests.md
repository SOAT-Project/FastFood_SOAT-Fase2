# 🧪 Integration Tests with Testcontainers

Este documento descreve como executar testes de integração usando a tech stack:

- Testcontainers
- PostgreSQL 

Os testes de integração são isolados dos testes unitários e ficam no diretório `src/testIntegration/java`.

---

## 📁 Estrutura de diretório
```
src/
├── main/java
├── test/java # Testes unitarios
└── testIntegration/java # Testes de integração
```

🔧 Perfil de teste: integration-test

Os testes de integração usam um perfil específico do Spring: **integration-test**. Isso permite isolar a configuração de outros ambientes.

Coloque seu arquivo de configuração aqui:

```
src/testIntegration/resources/application-integration-test.yml
```

## 🚀 Executando os testes de integração

Você pode executar os testes de integração usando a seguinte tarefa Gradle:

```
./gradlew testIntegration
```

You can also run all tests (unit + integration):

```
./gradlew check
```
