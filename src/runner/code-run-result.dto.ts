import { ApiProperty } from '@nestjs/swagger';

export class CodeRunResultDto {
  @ApiProperty({ example: 1, description: '실행 ID' })
  id: number;

  @ApiProperty({ example: true, description: '실행 결과 성공 여부' })
  result: boolean;

  @ApiProperty({ example: 23, description: '실행 시간 (ms)' })
  runtime: number;

  constructor(id: number, result: boolean, runtime: number) {
    this.id = id;
    this.result = result;
    this.runtime = runtime;
  }
}
