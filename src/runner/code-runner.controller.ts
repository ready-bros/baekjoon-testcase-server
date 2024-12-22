import { Controller, Post, Body } from '@nestjs/common';
import { CodeRunnerService } from "./code-runner.service";

interface CodeRunResponse {
    id: number;
    result: boolean;
    runtime: number;
}

@Controller('code-runner')
export class CodeRunnerController {
  constructor(
    private readonly codeRunnerService: CodeRunnerService
  ) {}

    @Post()
    async codeRun(
        @Body('code') code: string,
        @Body('input') input: string,
        @Body('answer') answer: string,
        @Body('timeLimitSecond') timeLimitSecond: number
    ): Promise<CodeRunResponse> {
      const result = await this.codeRunnerService.runCode(code, input, answer, timeLimitSecond);

        if (result.output === answer + '\n') {
            return {
              id: 1,
              result: true,
              runtime: result.runtime,
            }
        }

        return {
          id: 1,
          result: false,
          runtime: result.runtime,
        };
    }
}
