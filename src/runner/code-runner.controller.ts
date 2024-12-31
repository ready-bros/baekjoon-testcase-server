import { Controller, Post, Body } from '@nestjs/common';
import { CodeRunnerService } from './code-runner.service';
import { ApiBody, ApiOperation, ApiParam, ApiResponse } from '@nestjs/swagger';
import { CodeRunResultDto } from './code-run-result.dto';
import { CodeRunRequestDto } from './code-run-request.dto';

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
    type: CodeRunResultDto,
  })
  @Post()
  @ApiBody({ type: CodeRunRequestDto })
  async codeRun(@Body() request: CodeRunRequestDto): Promise<CodeRunResponse> {
    const result = await this.codeRunnerService.runCode(request);

    if (result.output === request.answer + '\n') {
      return new CodeRunResultDto(1, true, result.runtime);
    }

    return new CodeRunResultDto(1, false, result.runtime);
  }
}
