import { Controller, Get } from '@nestjs/common';
import { AppService } from './app.service';
import { ApiOperation, ApiProperty, ApiResponse } from '@nestjs/swagger';

class HealthCheckResponse {
  @ApiProperty({ example: true })
  success: boolean;

  @ApiProperty({ example: true })
  health: string;

  constructor(success: boolean, health: string) {
    this.success = success;
    this.health = health;
  }
}

@Controller()
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Get()
  getHello(): string {
    return this.appService.getHello();
  }

  @ApiOperation({ summary: 'health check' })
  @ApiResponse({
    status: 200,
    description: '성공',
    schema: {
      type: 'object',
      properties: {
        success: { type: 'boolean', example: true },
        health: { type: 'string', example: 'healthy' },
      },
    },
  })
  @Get('/health')
  healthCheck() {
    return new HealthCheckResponse(true, 'healthy');
  }
}
