import { spawn } from 'child_process';
import { Injectable } from '@nestjs/common';

interface CodeRunnerResult {
  output: string;
  runtime: number;
}

@Injectable()
export class CodeRunnerService {
  async runCode(
    code: string,
    input: string,
    timeLimitSecond: number,
  ): Promise<CodeRunnerResult> {
    return new Promise((resolve, reject) => {
      const child = spawn('node', ['-e', code], {
        stdio: ['pipe', 'pipe', 'pipe'],
      });
      let output = '';
      let runtime = 0;

      // console.log 결과 저장
      child.stdout.on('data', (data) => {
        output += data.toString();
      });

      child.stderr.on('data', (data) => {
        console.error(`stderr: ${data}`);
      });

      child.on('close', (code) => {
        console.log(`프로세스 종료: ${code}`);
        resolve({ output, runtime: Date.now() - runtime });
      });

      // node 스크립트 실행 with arguments
      setTimeout(() => {
        reject(new Error('Timeout'));
      }, timeLimitSecond * 1000);

      runtime = Date.now();
      child.stdin.write(input + '\n');
      child.stdin.end();
    });
  }
}
