# paypalIntegration

Aplicação Spring Boot que integra o checkout do PayPal: cria o pagamento, redireciona o usuário para aprovação e trata o retorno de sucesso, cancelamento e erro.

> Projeto de estudo (junho/2024) para entender o fluxo de pagamento por redirecionamento do PayPal de ponta a ponta.

## Stack

- Java 17
- Spring Boot 3.2.1 (Web, Thymeleaf)
- PayPal REST API SDK 1.14.0 e paypal-core 1.7.2
- Lombok

## Fluxo

1. `GET /` exibe o formulário de pagamento.
2. `POST /payment/create` monta o `Payment` no SDK e redireciona para a URL de aprovação do PayPal.
3. O usuário aprova (ou cancela) no site do PayPal.
4. O PayPal redireciona de volta para `/payment/success` (que executa o pagamento), `/payment/cancel` ou `/payment/error`.

## Rotas

| Método | Rota               | Descrição                                    |
|--------|--------------------|----------------------------------------------|
| `GET`  | `/`                | Formulário de pagamento                      |
| `POST` | `/payment/create`  | Cria o pagamento e redireciona para o PayPal |
| `GET`  | `/payment/success` | Executa e confirma o pagamento aprovado      |
| `GET`  | `/payment/cancel`  | Página de pagamento cancelado                |
| `GET`  | `/payment/error`   | Página de erro                               |

## Configuração

As credenciais ficam em `src/main/resources/application.yml`:

```yaml
paypal:
  client-id: SEU_CLIENT_ID
  client-secret: SEU_CLIENT_SECRET
  client-mode: sandbox   # sandbox para testes, live para produção
```

Pegue as suas em <https://developer.paypal.com/dashboard/applications/sandbox>.

> **Atenção:** o repositório hoje traz credenciais de **sandbox** commitadas no `application.yml`. Elas não movimentam dinheiro real, mas o padrão correto é externalizá-las em variáveis de ambiente e nunca versionar credenciais, inclusive as de sandbox.

## Como rodar

```bash
./mvnw spring-boot:run
```

Acesse <http://localhost:8080> e use uma conta comprador de sandbox para testar o pagamento.

## Limitações

Não há persistência dos pagamentos nem validação de webhook do PayPal: a confirmação depende exclusivamente do redirect de volta, que pode ser perdido se o usuário fechar o navegador. Valor e moeda estão fixos no controller.
