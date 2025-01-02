import { NestFactory } from '@nestjs/core';
import { AppModule } from './app.module';
import { DocumentBuilder, SwaggerModule } from '@nestjs/swagger';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);

  const options = new DocumentBuilder()
    .setTitle('Baekjoon testcase API')
    .setDescription('API description')
    .setVersion('1.0')
    .addServer('http://localhost:3000/api', 'Local environment')
    .build();
  const document = SwaggerModule.createDocument(app, options);

  app.enableCors({
    origin: '*',
    methods: 'GET,HEAD,PUT,PATCH,POST,DELETE',
  });
  SwaggerModule.setup('api-docs', app, document);

  app.enableShutdownHooks();

  await app.listen(process.env.PORT ?? 3000).then(() => {
    process.on('SIGTERM', async () => {
      await app.close();
    });
  });
}
bootstrap();
