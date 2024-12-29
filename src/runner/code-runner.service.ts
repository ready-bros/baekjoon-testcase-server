import { Injectable } from '@nestjs/common';
import { CodeRunnerResult, LANGUAGE_MAP } from '../util/constant';

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
      throw new Error('지원하지 않는 언어입니다.');
    }
    const instance = new LanguageClass(timeLimitSecond, code, input);
    return instance.runCode();
  }
}
