# 📦 FastFood API — Deploy no Kubernetes

Este projeto contém os manifests necessários para executar a API FastFood em um cluster Kubernetes com PostgreSQL, escalonamento automático (HPA) e probes de saúde.


✅ Passos para rodar no Kubernetes

### 1. Aplicar os recursos da aplicação

Execute o seguinte comando dentro da pasta `kubernetes/`:

```bash
kubectl apply -f k8s.yaml
```

Isso criará:

- `ConfigMap` e `Secret` com as configurações da aplicação e do banco
- Banco de dados PostgreSQL com volume persistente (`StatefulSet`)
- API FastFood (`Deployment`)
- Serviços (`ClusterIP` para o banco, `LoadBalancer` para a aplicação)
- Escalonamento automático (`HorizontalPodAutoscaler`)

---

### 2. Instalar o Metrics Server

O HPA depende do `metrics-server` para funcionar corretamente. Instale com:

```bash
kubectl apply -f metrics-server.yaml
```

### 3. Acessar a API

#### 🔁 Opção 1: Port Forward (acesso local)

```bash
kubectl port-forward svc/fastfood-service 8080:80
```

- Acesse a API: [http://localhost:8080](http://localhost:8080)
- Swagger: [http://localhost:8080/api/swagger-ui/index.html](http://localhost:8080/api/swagger-ui/index.html)

---

#### 🌐 Opção 2: LoadBalancer com MetalLB (clusters locais)

Instale o MetalLB (se estiver usando kind ou similar):

```bash
kubectl apply -f https://raw.githubusercontent.com/metallb/metallb/v0.13.7/config/manifests/metallb-native.yaml
```

Após aplicar, o serviço `fastfood-service` receberá um IP externo. Exemplo:

```bash
kubectl get svc fastfood-service
```

Saída esperada:

```
NAME               TYPE           CLUSTER-IP     EXTERNAL-IP     PORT(S)        AGE
fastfood-service   LoadBalancer   10.96.0.150    172.18.255.200  80:30215/TCP   2m
```

Acesse a API em: [http://172.18.255.200](http://172.18.255.200)  
Acesse o Swagger em: [http://172.18.255.200/api/swagger-ui/index.html](http://172.18.255.200/api/swagger-ui/index.html)
OBS: EXTERNAL-IP DE EXEMPLO

---

### 4. Verificar funcionamento

Teste o endpoint de saúde:

```bash
http://localhost:8080/api/actuator/health
```

---

## 📊 Sobre o HPA (Horizontal Pod Autoscaler)

A aplicação está configurada para escalar com base no uso de CPU:

- Mínimo de 3 réplicas
- Máximo de 10 réplicas
- Escala quando o uso médio de CPU ultrapassa 50%

Verifique com:

```bash
kubectl get hpa
```

---

## 📁 Estrutura de Arquivos

```
kubernetes/
├── k8s.yaml              # Recursos da aplicação, banco, serviços, HPA etc.
└── metrics-server.yaml   # Instalação do metrics-server
```
