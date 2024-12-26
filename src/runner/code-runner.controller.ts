import { Controller, Post, Body } from '@nestjs/common';
import { CodeRunnerService } from './code-runner.service';
import { ApiOperation, ApiResponse } from '@nestjs/swagger';
import { CodeRunResultDto } from './code-run-result.dto';

interface CodeRunResponse {
  id: number;
  result: boolean;
  runtime: number;
}

@Controller('code-runner')
export class CodeRunnerController {
  constructor(private readonly codeRunnerService: CodeRunnerService) {}

  @ApiOperation({ summary: '코드 실행 api' })
  @ApiResponse({
    status: 200,
    description: '성공',
    schema: {
      type: 'object',
      properties: {
        id: { type: 'number', example: 1 },
        result: { type: 'boolean', example: true },
        runtime: { type: 'number', example: 23 },
      },
    },
  })
  @Post()
  async codeRun(
    @Body('code') code: string,
    @Body('input') input: string,
    @Body('answer') answer: string,
    @Body('timeLimitSecond') timeLimitSecond: number,
  ): Promise<CodeRunResponse> {
    const result = await this.codeRunnerService.runCode(
      code,
      input,
      answer,
      timeLimitSecond,
    );

    if (result.output === answer + '\n') {
      return new CodeRunResultDto(1, true, result.runtime);
    }

    return new CodeRunResultDto(1, false, result.runtime);
  }
}
