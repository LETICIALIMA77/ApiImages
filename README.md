# ApiImages


Esta aplicação fornece um endpoint de API RESTful para adicionar marcas d'água em imagens. Ela recebe uma imagem codificada em base64 e um texto de marca d'água como entrada, aplica a marca d'água na imagem, salva a imagem com marca d'água em um diretório e retorna a URL da imagem com marca d'água.

## Como Usar

1. **Enviar Requisição POST**

   Envie uma requisição POST para `/api/images/watermark` com o conteúdo JSON contendo a imagem codificada em base64 e o texto da marca d'água.

   Exemplo de Corpo da Requisição:
   ```json
   {
       "base64Image": "<imagem codificada em base64>",
       "watermarkText": "Seu Texto de Marca d'Água"
   }
   ```

2. **Receber Resposta**

   Após o processamento bem-sucedido, a API responderá com um objeto JSON contendo a imagem codificada em base64 (para referência) e a URL da imagem com marca d'água.

   Exemplo de Corpo da Resposta:
   ```json
   {
       "base64Image": "<imagem codificada em base64>",
       "imageUrl": "images/<nome de arquivo único da imagem>.png"
   }
   ```

3. **Acessar a Imagem com Marca d'Água**

   Você pode acessar a imagem com marca d'água usando a URL fornecida.

## Dependências

- Spring Boot
- API de Imagens Avançadas em Java (javax.imageio)
- API Base64 em Java (java.util.Base64)
- API UUID em Java (java.util.UUID)

## Detalhes de Implementação

- **ImageController**: Esta classe lida com as requisições recebidas. Ela decodifica a imagem codificada em base64, aplica a marca d'água usando o texto fornecido, salva a imagem com marca d'água em um diretório e retorna a URL da imagem com marca d'água.

- **ImageRequest**: Representa o corpo da requisição contendo a imagem codificada em base64 e o texto da marca d'água.

- **ImageResponse**: Representa o corpo da resposta contendo a imagem codificada em base64 (para referência) e a URL da imagem com marca d'água.

- **applyWatermark**: Um método auxiliar para aplicar a marca d'água na imagem original usando o texto e a posição fornecidos.

## Configuração

- **IMAGE_DIRECTORY**: O diretório onde as imagens originais e com marca d'água são temporariamente armazenadas.

## Notas Importantes

- A marca d'água é adicionada em vermelho, com fonte Arial e tamanho de fonte 30, posicionada em (50, 50) na imagem. Você pode personalizar esses parâmetros no método `applyWatermark`.

- A aplicação assume que a imagem codificada em base64 fornecida está no formato PNG. Você pode ajustar o formato da imagem conforme necessário no método `ImageIO.write`.

- Esta aplicação não inclui mecanismos de autenticação ou autorização. Garanta que medidas de segurança adequadas sejam implementadas antes de implantá-la em um ambiente de produção.

## Executando a Aplicação

Para executar a aplicação, execute o método `main` na classe `ImageWatermarkApplicationController`. A aplicação iniciará e ficará ouvindo por requisições na porta configurada.
