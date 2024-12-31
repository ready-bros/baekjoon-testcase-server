import { Injectable } from '@nestjs/common';
import { CodeRunnerResult, LANGUAGE_MAP } from '../util/constant';
import { NotSupportedLanguageException } from '../exception/domain/not-supported-language.exception';
import { CodeRunRequestDto } from './code-run-request.dto';

@Injectable()
export class CodeRunnerService {
  async runCode(
    codeRunRequestDto: CodeRunRequestDto,
  ): Promise<CodeRunnerResult> {
    const { language, code, input, timeLimitSecond } = codeRunRequestDto;
    const LanguageClass = LANGUAGE_MAP.get(language);
    if (LanguageClass === undefined) {
      throw new NotSupportedLanguageException(codeRunRequestDto.language);
    }

    const instance = new LanguageClass(timeLimitSecond, code, input);
    return instance.runCode();
  }
}
