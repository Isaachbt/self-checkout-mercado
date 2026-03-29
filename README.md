# Self-Checkout

Sistema para supermercado atacado e varejistas, completo com todas as funcionalidades.

## 🔎 Sobre
Sistema para automatizar na hora de passar as compras, formas simples e rapida de varios clientes passar sem precisar de inumeros funcionarios.

## 🚀 Tecnologias
- Java 17+  
- Spring Boot
- PostgreSQL
- Spring security
- Lombok

## 🔐 Auth
- Usuario cadastrado via ADM direto no banco
- Necessario cracha + senha
- Login no self: cracha + senha + id Totem

## 📥 Pré-requisitos
Antes de usar esta API, instale:
1. **Java 17 ou superior**
2. **Maven**
3. PostgreSQL

## 🔧 Como rodar
1. Clone o repositório:  
```
   git clone https://github.com/Isaachbt/self-checkout-mercado.git
```
2. Navegue ate o diretorio:
```
   cd self-checkout-mercado
```
4. Execute o aplicativo Spring Boot:
```
   mvn clean install
   mvn spring-boot:run
```
5. Configurações

```
spring.datasource.url=…
spring.datasource.username=…
spring.datasource.password=…
```

## Funcionalidades

Esta API oferece as seguintes funcionalidades/endpoint:
* "api/"

Iniciar uma session no totem
```
http://localhost:8080/checkout/sessions
```
Encerrar session no totem
```
http://localhost:8080/checkout/sessions/{id}/{logId}/cancel
```
Add itens no carrinho
```
http://localhost:8080/checkout/sessions/{idSession}/items
```
Remover itens do carrinho
```
http://localhost:8080/checkout/session/{id}/{idSession}/{idProduct}/remove-items
```
Login do usuario:  "/auth"
```
http://localhost:8080//login
```
Pagamento: 
* "payment/"
```
http://localhost:8080/checkout/session/{id}/payments
```

## Exemplo de requisição

```
POST http://localhost:8080/checkout/sessions
{
    "id": 3,
    "status": "OPEN",
    "createdAt": "2026-03-29T17:21:06.5930757",
    "totalAmount": 0.0,
    "audiId": "8f001522-e1ca-4444-8a5e-469e5f45ee70"
}

POST http://localhost:8080/checkout/sessions/{idSession}/items
{
  "barcode": "7851000300301",
  "quantity": 2,
  "weight": 0
}
```

## OBS
Uma lista de produtos por KG e UN e salva no banco ao iniciar.

# Segurança
Este aplicativo implementa a segurança usando o Spring Security. As seguintes características de segurança estão em vigor:

- Autenticação de usuários.
- Autorização baseada em funções de usuário.
- Tokens com tempo.

- Acesso não autorizado: 403
- Não encontrado: 404

## 💡 Novas funcionalidades: 
- Junit
- Front-end com leitura de codigo de barras
- disponibilizar online

# Contribuição
Sinta-se à vontade para contribuir com novos recursos, correções de bugs ou melhorias de desempenho. Basta enviar um pull request!

# Licença
Este projeto é licenciado sob a [MIT License](LICENSE).
