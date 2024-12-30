import { Injectable } from '@nestjs/common';
import { CodeRunnerResult, LANGUAGE_MAP } from '../util/constant';
import { NotSupportedLanguageException } from '../exception/domain/not-supported-language.exception';

@Injectable()
export class CodeRunnerService {
  async runCode(
    language: string,
    code: string,
    input: string,
    timeLimitSecond: number,
  ): Promise<CodeRunnerResult> {
    const LanguageClass = LANGUAGE_MAP.get(language);
    if (LanguageClass === undefined) {
      throw new NotSupportedLanguageException(language);
    }

    const instance = new LanguageClass(timeLimitSecond, code, input);
    return instance.runCode();
  }
}
